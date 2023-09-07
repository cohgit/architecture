package cl.atianza.remove.external.clients.email;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.atianza.remove.dtos.commons.ConfigurationDto;
import cl.atianza.remove.enums.EMessageBundleKeys;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.exceptions.RemoveExceptionHandler;
import cl.atianza.remove.models.ConfigurationEmail;
import cl.atianza.remove.properties.RemoveCommonsProperties;

/**
 * Class for emails sent by the system.
 * @author pilin
 *
 */
public class RemoveEmailClient {
	private Logger log = LogManager.getLogger(RemoveEmailClient.class);
	private static final Pattern REG_EXP_IMG_TAG_BASE_64  = Pattern.compile( "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>" );
	
	private String host = "";
	private String username = "";
	private String password = "";
	
	private String from = "";
	private Properties props = new Properties();
//	private Map<String, String> inlineImagesBase64 = new HashMap<String, String>();
	private List<ImageAttachBase64> lstImages = new ArrayList<ImageAttachBase64>();
	
	private boolean isActive = RemoveCommonsProperties.init().getProperties().isEmails_active();
	
	public RemoveEmailClient(String username, String password, String from, String host) {
		super();
		this.username = username;
		this.password = password;
		this.from = from;
		this.host = host;
	}

	public static RemoveEmailClient init(String username, String password, String from, String host) {
		return new RemoveEmailClient(username, password, from, host);
	}
	
	public static RemoveEmailClient init(ConfigurationEmail config) {
		RemoveEmailClient client = new RemoveEmailClient(config.getUserName(), config.getPassword(), config.getEmailFrom(), config.getHost());
		client.prepareProps(config.getParams());
		
		return client;
	}

	public void send(RemoveMail mail) throws RemoveApplicationException {
		log.info("Sending email: " + mail + " isActive: " + isActive);
//		log.info("Props ready: " + props);
//		log.info("from: " + this.from);
		// log.info("content:" + mail.getContent());
		
		if (isActive) {
//			props.keySet().forEach(key ->{
//				log.info("prop: " + key +" - "+props.getProperty((String) key));
//			});
			
			Session session = Session.getDefaultInstance(props);
			
			/*Session session = Session.getInstance(props, new Authenticator() {
			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(username, password);
			    }
			});*/
			
			// Used to debug SMTP issues
	        // session.setDebug(true);
			
			try {
//				log.debug("Transporting...");
				Transport transport = session.getTransport();
//				log.debug("Connecting transport with session email: " + username + " - " + password);
				transport.connect(host, username, password);
				// Transport.send(createMessageForSMTP(session, mail));
				Message msg = createMessageForSMTP(session, mail);
//				log.debug("Ready To Send... ");
				transport.sendMessage(msg, msg.getAllRecipients());
				log.debug("Email Sent...");
			} catch (MessagingException e) {
				log.error("Error sending email: ", e);
				RemoveExceptionHandler.throwAppException(EMessageBundleKeys.ERROR_SENDING_EMAIL);
			}
		}
	}
	
	private Message createMessageForSMTP(Session session, RemoveMail mail) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setHeader("Content-Type", "text/html; charset=UTF-8");
		message.setFrom(new InternetAddress(from));
	
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
		message.setSubject(mail.parseSubject(), "utf-8");
		 
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(cleanContent(mail.parseContent()), mail.getType());
		 
		Multipart multipart = new MimeMultipart();
		attachFiles(multipart, mail);
		
		multipart.addBodyPart(bodyPart);
		message.setContent(multipart, "utf-8");
		
		return message;
	}
	
	private String cleanContent(String content) {
		Matcher matcher = REG_EXP_IMG_TAG_BASE_64.matcher(content);
		int i = 0;
		while (matcher.find()) {
			String src = matcher.group();
			if (content.indexOf(src) != -1) {
				String srcToken = "src=\"";
				int x = src.indexOf( srcToken );
				int y = src.indexOf( "\"", x + srcToken.length() );
				String srcText = src.substring( x + srcToken.length(), y );
				
				if (srcText.startsWith("data:")) {
					ImageAttachBase64 image = new ImageAttachBase64();
					image.setKey("image" + i);
					image.prepareContent(srcText);
					String newSrc = src.replace( srcText, "cid:" + image.getKey() );
					content = content.replace( src, newSrc );
					this.lstImages.add(image);
					i++;	
				}
			}
		}
		
		return content;
	}

	private void attachFiles(Multipart multipart, RemoveMail mail) throws MessagingException {
		if (this.lstImages != null && !this.lstImages.isEmpty()) {
			log.info("There are images to attach: " + this.lstImages.size());
			this.lstImages.forEach(image -> {
				log.info("Attaching image: " + image);
				byte[] rawImage = Base64.getDecoder().decode(image.getData());
				
				BodyPart imagePart = new MimeBodyPart();
				ByteArrayDataSource imageDataSource = new ByteArrayDataSource(rawImage, image.getType());
				
		        try {
		        	imagePart.setDataHandler(new DataHandler(imageDataSource));
					imagePart.setHeader("Content-ID", "<" + image.getKey() + ">");
					imagePart.setFileName(image.getKey() + "." + image.extractExtention());
			        multipart.addBodyPart(imagePart);
				} catch (MessagingException e) {
					log.error("Error attaching image: ", e);
				}
			});
		}
		
		if (mail.getFiles() != null && !mail.getFiles().isEmpty()) {
			log.info("There are files to attach: " + mail.getFiles().size());
			
			mail.getFiles().forEach(filePath -> {
				try {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					/*MimeBodyPart attachmentBodyPart = new MimeBodyPart();
					attachmentBodyPart.attachFile(new File(filePath));
					multipart.addBodyPart(attachmentBodyPart);*/
					
					DataSource source = new FileDataSource(filePath);
					attachmentPart.setDataHandler(new DataHandler(source));
					attachmentPart.setFileName(filePath);
					
					multipart.addBodyPart(attachmentPart);
					log.info("File Attached: " + filePath);
				} catch (MessagingException e) {
					log.error("Error attaching file: ", e);
				}
			});
		}
	}

	private void prepareProps(List<ConfigurationDto> params) {
		if (params != null) {
			params.forEach(param -> {
				props.put(param.getKey(), param.getValue());	
			});
		}
		
		props.put("mail.smtp.user", this.username);
		props.put("mail.smtp.password", this.password);
//		log.info("Props ready: " + props);
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}

class ImageAttachBase64 {
	private String key;
	private String type;
	private String data;
	
	public String getKey() {
		return key;
	}
	public void prepareContent(String srcText) {
		String[] srcSplited = srcText.split(",");
		
		if (srcSplited.length == 2) {
			this.setData(srcSplited[1]);
			this.setType(srcSplited[0].split(":")[1].split(";")[0]);
		}
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String extractExtention() {
		if (this.getType() != null) {
			String[] typeSplited = this.getType().split("/");
			
			if (typeSplited.length == 2) {
				return typeSplited[1];
			}
		}
		
		return "";
	}
	@Override
	public String toString() {
		return "ImageAttachBase64 [key=" + key + ", type=" + type + "]";
	}
}
