package cl.atianza.remove.enums;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Enum with a list of default system languages.
 * @author pilin
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = LanguageSerializer.class)
public enum ELanguages {
	NOLANGUAGE("NoLanguage", "ni"),
	ENGLISH("English", "en"),
	SPANISH("Spanish", "es"),
	AFRIKAANS("Afrikaans", "af"),
	AKAN("Akan", "ak"),
	ALBANIAN("Albanian", "sq"),
	AMHARIC("Amharic", "am"),
	ARABIC("Arabic", "ar"),
	ARMENIAN("Armenian", "hy"),
	AZERBAIJANI("Azerbaijani", "az"),
	BASQUE("Basque", "eu"),
	BELARUSIAN("Belarusian", "be"),
	BEMBA("Bemba", "bem"),
	BENGALI("Bengali", "bn"),
	BIHARI("Bihari", "bh"),
	BOSNIAN("Bosnian", "bs"),
	BRETON("Breton", "br"),
	BULGARIAN("Bulgarian", "bg"),
	CAMBODIAN("Cambodian", "km"),
	CATALAN("Catalan", "ca"),
	CHEROKEE("Cherokee", "chr"),
	CHICHEWA("Chichewa", "ny"),
	CHINESE_SIMPLIFIED("Chinese Simplified", "zh-cn"),
	CHINESE_TRADITIONAL("Chinese Traditional", "zh_tw"),
	CORSICAN("Corsican", "co"),
	CROATIAN("Croatian", "hr"),
	CZECH("Czech", "cs"),
	DANISH("Danish", "da"),
	DUTCH("Dutch", "nl"),
	ESPERANTO("Esperanto", "eo"),
	ESTONIAN("Estonian", "et"),
	EWE("Ewe", "ee"),
	FAROESE("fo", "fo"),
	FILIPINO("Filipino", "tl"),
	FINNISH("Finnish", "fi"),
	FRENCH("French", "fr"),
	FRISIAN("Frisian", "fy"),
	GA("Ga", "gaa"),
	GALICIAN("Galician", "gl"),
	GEORGIAN("Georgian", "ka"),
	GERMAN("German", "de"),
	GREEK("Greek", "el"),
	GUARANI("Guarani", "gn"),
	GUJARATI("Gujarati", "gu"),
	HAITIAN_CREOLE("Haitian Creole", "ht"),
	HAUSA("Hausa", "ha"),
	HAWAIIAN("Hawaiian", "haw"),
	HEBREW("Hebrew", "iw"),
	HINDI("Hindi", "hi"),
	HUNGARIAN("Hungarian", "hu"),
	ICELANDIC("Icelandic", "is"),
	IGBO("Igbo", "ig"),
	INDONESIAN("Indonesian", "id"),
	INTERLINGUA("Interlingua", "ia"),
	IRISH("Irish", "ga"),
	ITALIAN("Italian", "it"),
	JAPANESE("Japanese", "ja"),
	JAVANESE("Javanese", "jw"),
	KANNADA("Kannada", "kn"),
	KAZAKH("Kazakh", "kk"),
	KINYARWANDA("Kinyarwanda", "rw"),
	KIRUNDI("Kirundi", "rn"),
	KONGO("Kongo", "kg"),
	KOREAN("Korean", "ko"),
	KRIO("Krio (Sierra Leone)", "kri"),
	KURDISH("Kurdish", "ku"),
	KURDISH_SORANI("Kurdish (Sorani)", "ckb"),
	KYRGYZ("Kyrgyz", "ky"),
	LAOTHIAN("Laothian", "lo"),
	LATIN("Latin", "la"),
	LATVIAN("Latvian", "lv"),
	LINGALA("Lingala", "ln"),
	LITHUANIAN("Lithuanian", "lt"),
	LOZI("Lozi", "loz"),
	LUGANDA("Luganda", "lg"),
	LUO("Luo", "ach"),
	MACEDONIAN("Macedonian", "mk"),
	MALAGASY("Malagasy", "mg"),
	MALAY("Malay", "ms"),
	MALAYALAM("Malayalam", "ml"),
	MALTESE("Maltese", "mt"),
	MAORI("Maori", "mi"),
	MARATHI("Marathi", "mr"),
	MAURITIAN_CREOLE("Mauritian Creole", "mfe"),
	MOLDAVIAN("Moldavian", "mo"),
	MONGOLIAN("Mongolian", "mn"),
	MONTENEGRIN("Montenegrin", "sr-ME"),
	NEPALI("Nepali", "ne"),
	NIGERIAN_PIDGIN("Nigerian Pidgin", "pcm"),
	NOTHERN_SOTHO("Nothern Sotho", "nso"),
	NORWEGIAN("Norwegian", "no"),
	NORWEGIAN_NYNORSK("Norwegian Nynorsk", "nn"),
	OCCITAN("Occitan", "oc"),
	ORIYA("Oriya", "or"),
	OROMO("Oromo", "om"),
	PASHTO("Pashto", "ps"),
	PERSIAN("Persian", "fa"),
	POLISH("Polish", "pl"),
	PORTUGUESE("Portuguese", "pt"),
	PORTUGUESE_BRAZIL("Portuguese (Brazil)", "pt-br"),
	PORTUGUESE_PORTUGAL("Portuguese (Portugal)", "pt-pt"),
	PUNJABI("Punjabi", "pa"),
	QUECHUA("Quechua", "qu"),
	ROMANIAN("Romanian", "ro"),
	ROMANSH("Romansh", "rm"),
	RUNYAKITARA("Runyakitara", "nyn"),
	RUSSIAN("Russian", "ru"),
	SCOTS_GAELIC("Scots Gaelic", "gd"),
	SERBIAN("Serbian", "sr"),
	SERBO_CROATIAN("Serbo-Croatian", "sh"),
	SESOTHO("Sesotho", "st"),
	SETSWANA("Setswana", "tn"),
	SEYCHELLOIS_CREOLE("Seychellois Creole", "crs"),
	SHONA("Shona", "sn"),
	SINDHI("Sindhi", "sd"),
	SINHALESE("Sinhalese", "si"),
	SLOVAK("Slovak", "sk"),
	SLOVENIAN("Slovenian", "sl"),
	SOMALI("Somali", "so"),
	SPANISH_LATIN_AMERICAN("Spanish (Latin American)", "es-419"),
	SUNDANESE("Sundanese", "su"),
	SWAHILI("Swahili", "sw"),
	SWEDISH("Swedish", "sv"),
	TAJIK("Tajik", "tg"),
	TAMIL("Tamil", "ta"),
	TATAR("Tatar", "tt"),
	TELUGU("Telugu", "te"),
	THAI("Thai", "th"),
	TIGRINYA("Tigrinya", "ti"),
	TONGA("Tonga", "to"),
	TSHILUBA("Tshiluba", "lua"),
	TUMBUKA("Tumbuka", "tum"),
	TURKISH("Turkish", "tr"),
	TURKMEN("Turkmen", "tk"),
	TWI("Twi", "tw"),
	UIGHUR("Uighur", "ug"),
	UKRAINIAN("Ukrainian", "uk"),
	URDU("Urdu", "ur"),
	UZBEK("Uzbek", "uz"),
	VIETNAMESE("Vietnamese", "vi"),
	WELSH("Welsh", "cy"),
	WOLOF("Wolof", "wo"),
	XHOSA("Xhosa", "xh"),
	YIDDISH("Yiddish", "ys"),
	YORUBA("Yoruba", "yo"),
	ZULU("Zulu", "zu")
	;
	
	private String language;
	private String code;
	private boolean active = true;
	
	private ELanguages(String language, String code) {
		this.language = language;
		this.code = code;
	}
	private ELanguages(String language, String code, boolean active) {
		this.language = language;
		this.code = code;
		this.active = active;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCollectionName() {
		return "country"+this.code.toUpperCase();
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@JsonCreator
    static ELanguages findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static ELanguages obtain(String code) {
		for(ELanguages d : ELanguages.values()) {
			if (d.getCode().equalsIgnoreCase(code)) return d;
		}
		
		return null;
	}
}

class LanguageSerializer extends StdSerializer<ELanguages> {
	private static final long serialVersionUID = -8761216512740416212L;

	public LanguageSerializer() {
		super(ELanguages.class);
	}
	
	protected LanguageSerializer(Class<ELanguages> t) {
		super(t);
	}

	@Override
	public void serialize(ELanguages value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeFieldName("language");
        generator.writeString(value.getLanguage());
        generator.writeEndObject();
	}
}