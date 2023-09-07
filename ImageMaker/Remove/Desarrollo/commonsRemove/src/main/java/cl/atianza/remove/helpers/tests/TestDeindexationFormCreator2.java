package cl.atianza.remove.helpers.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.atianza.remove.daos.commons.DynamicFormDao;
import cl.atianza.remove.enums.EDynamicFormTypes;
import cl.atianza.remove.enums.ELanguages;
import cl.atianza.remove.exceptions.RemoveApplicationException;
import cl.atianza.remove.models.commons.DynamicForm;
import cl.atianza.remove.models.commons.DynamicFormConditionResult;
import cl.atianza.remove.models.commons.DynamicFormConditionResultLabel;
import cl.atianza.remove.models.commons.DynamicFormInput;
import cl.atianza.remove.models.commons.DynamicFormInputDescription;
import cl.atianza.remove.models.commons.DynamicFormInputLabel;
import cl.atianza.remove.models.commons.DynamicFormInputOption;
import cl.atianza.remove.models.commons.DynamicFormInputOptionsLabel;
import cl.atianza.remove.models.commons.DynamicFormSection;
import cl.atianza.remove.models.commons.DynamicFormSectionLabel;
import cl.atianza.remove.utils.RemoveDateUtils;

public class TestDeindexationFormCreator2 {
	public void createVersionOneDotOne() throws RemoveApplicationException {
		DynamicFormDao.init().deactiveAll();
		DynamicForm form = new DynamicForm();
		
		form.setActive(true);
		form.setType(EDynamicFormTypes.DEINDEXATION.getTag());
		form.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		form.setVersion("1.2");
		form.setSections(createTestSections());
		
		createGoogleResults(form);
		createMediaResults(form);
		
		form = DynamicFormDao.init().save(form);
		DynamicFormDao.init().active(form);
	}

	private List<DynamicFormSection> createTestSections() {
		List<DynamicFormSection> lst = new ArrayList<DynamicFormSection>();
		
		lst.add(createSection1()); // La Persona
		lst.add(createSection2()); // El Contenido
		
		return lst;
	}
	
	private DynamicFormSection createSection1() {
		DynamicFormSection section1 = new DynamicFormSection();
		section1.setActive(true);
		section1.setPosition(1);
		createQuestion1(section1);
		createQuestion2(section1);
		createQuestion3(section1);
		section1.addLabel(new DynamicFormSectionLabel("La Persona", ELanguages.SPANISH.getCode()));
		section1.addLabel(new DynamicFormSectionLabel("The Person", ELanguages.ENGLISH.getCode()));
		return section1;
	}

	/**
	 * 
	 * @param section1 
	 * @return
	 */
	private void createQuestion1(DynamicFormSection section) {
		DynamicFormInput input1 = new DynamicFormInput("IS_EUROPEAN", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 1);
		
		input1.addLabel(new DynamicFormInputLabel("¿Eres ciudadano europeo?", ELanguages.SPANISH.getCode()));
		input1.addLabel(new DynamicFormInputLabel("Are you european?", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input1, "IS_EUROPEAN");
		
		section.addInput(input1);
		
		DynamicFormInput input1_2 = new DynamicFormInput("PERSON_COUNTRY", 
				"SELECT", 
				12, 
				false, "IS_EUROPEAN = IS_EUROPEAN_YES",
				false, "IS_EUROPEAN = IS_EUROPEAN_YES",
				true, null,
				null, null, 
				true, 2);
		
		input1_2.addLabel(new DynamicFormInputLabel("¿En cuál pais deseas borrar y/o desindexar el contenido?", ELanguages.SPANISH.getCode()));
		input1_2.addLabel(new DynamicFormInputLabel("In which country do you want to delete and / or de-index the content?", ELanguages.ENGLISH.getCode()));
		
		input1_2.addOption(createOption("GERMANY", "Alemania", "Germany"));
		input1_2.addOption(createOption("ANDORRA", "Andorra", "Andorra"));
		input1_2.addOption(createOption("AUSTRIA", "Austria", "Austria"));
		input1_2.addOption(createOption("BELGIUM", "Bélgica", "Belgium"));
		input1_2.addOption(createOption("BULGARIA", "Bulgaria", "Bulgaria"));
		input1_2.addOption(createOption("Canarias", "Canarias", "Canary Island"));
		input1_2.addOption(createOption("CHEQUIA", "Chequia", "Czechia"));
		input1_2.addOption(createOption("CYPRUS", "Chipre", "Cyprus"));
		input1_2.addOption(createOption("CROATIA", "Croacia", "Croatia"));
		input1_2.addOption(createOption("DENMARK", "Dinamarca", "Denmark"));
		input1_2.addOption(createOption("SLOVAKIA", "Eslovaquia", "Slovakia"));
		input1_2.addOption(createOption("SLOVENIA", "Eslovenia", "Slovenia"));
		input1_2.addOption(createOption("SPAIN", "España", "Spain"));
		input1_2.addOption(createOption("ESTONIA", "Estonia", "Estonia"));
		input1_2.addOption(createOption("FINLAND", "Finlandia", "Finland"));
		input1_2.addOption(createOption("FRANCE", "Francia", "France"));
		input1_2.addOption(createOption("GIBRALTAR", "Gibraltar", "Gibraltar"));
		input1_2.addOption(createOption("GREECE", "Grecia", "Greece"));
		input1_2.addOption(createOption("GUADELOUPE", "Guadalupe", "Guadeloupe"));
		input1_2.addOption(createOption("FRENCH_GUIANA", "Guayana Francesa", "French Guiana"));
		input1_2.addOption(createOption("GUERNSEY", "Guernesey", "Guernsey"));
		input1_2.addOption(createOption("HUNGARY", "Hungría", "Hungary"));
		input1_2.addOption(createOption("IRELAND", "Irlanda", "Ireland"));
		input1_2.addOption(createOption("ISLE_OF_MAN", "Isla de Man", "Isle of Man"));
		input1_2.addOption(createOption("ICELAND", "Islandia", "Iceland"));
		input1_2.addOption(createOption("ALAND_ISLANDS", "Islas Aland", "Aland Islands"));
		input1_2.addOption(createOption("ITALY", "Italia", "Italy"));
		input1_2.addOption(createOption("JERSEY", "Jersey", "Jersey"));
		input1_2.addOption(createOption("LATVIA", "Letonia", "Latvia"));
		input1_2.addOption(createOption("LIECHTENSTEIN", "Liechtenstein", "Liechtenstein"));
		input1_2.addOption(createOption("LITHUANIA", "Lituania", "Lithuania"));
		input1_2.addOption(createOption("LUXEMBOURG", "Luxemburgo", "Luxembourg"));
		input1_2.addOption(createOption("MALTA", "Malta", "Malta"));
		input1_2.addOption(createOption("MARTINIQUE", "Martinica", "Martinique"));
		input1_2.addOption(createOption("MAYOTTE", "Mayotte", "Mayotte"));
		input1_2.addOption(createOption("MONACO", "Monaco", "Monaco"));
		input1_2.addOption(createOption("NORWAY", "Noruega", "NORWAY"));
		input1_2.addOption(createOption("NEW_CALEDONIA", "Nueva Caledonia", "NEW_CALEDONIA"));
		input1_2.addOption(createOption("NETHERLANDS", "Paises Bajos", "Netherlands"));
		input1_2.addOption(createOption("FRENCH_POLYNESIA", "Polinesia Francesa", "French Polynesia"));
		input1_2.addOption(createOption("POLAND", "Polonia", "Poland"));
		input1_2.addOption(createOption("PORTUGAL", "Portugal", "Portugal"));
		input1_2.addOption(createOption("UNITED_KINGDOM", "Reino Unido", "United Kingdom"));
		input1_2.addOption(createOption("REUNION", "Reunión", "Réunion"));
		input1_2.addOption(createOption("ROMANIA", "Rumanía", "Romania"));
		input1_2.addOption(createOption("SAN_BARTOLOME", "San Bartolomé", "San Bartolome"));
		input1_2.addOption(createOption("SAN_MARINO", "San Marino", "San Marino"));
		input1_2.addOption(createOption("SAN_MARTIN", "San Martín", "San Martin"));
		input1_2.addOption(createOption("SAINT_P_AND_M", "San Pedro y Miquelón", "Saint Pierre and Miquelon"));
		input1_2.addOption(createOption("SWEDEN", "Suecia", "Sweden"));
		input1_2.addOption(createOption("SWITZERLAND", "Suiza", "Switzerland"));
		input1_2.addOption(createOption("WALLIS_FUTUNA", "Wallis y Futuna", "Wallis and Futuna"));
		
		section.addInput(input1_2);
	}

	private void createQuestion2(DynamicFormSection section) {
		DynamicFormInput input2 = new DynamicFormInput("IS_NOTORIOUS", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 3);
		
		input2.addLabel(new DynamicFormInputLabel("¿Eres una persona notoria?", ELanguages.SPANISH.getCode()));
		input2.addLabel(new DynamicFormInputLabel("Are you a notorious person? ", ELanguages.ENGLISH.getCode()));
		
		input2.addDescription(new DynamicFormInputDescription("Eres de interes público o has estado expuesto en medios de comunicación.", ELanguages.SPANISH.getCode()));
		input2.addDescription(new DynamicFormInputDescription("You are of public interest or have been exposed in the media.", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input2, "IS_NOTORIOUS");
		
		section.addInput(input2);
	}
	
	private void createQuestion3(DynamicFormSection section) {
		DynamicFormInput input3 = new DynamicFormInput("IS_MINOR", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 4);
		
		input3.addLabel(new DynamicFormInputLabel("¿La información es sobre un menor de edad?", ELanguages.SPANISH.getCode()));
		input3.addLabel(new DynamicFormInputLabel("Is it the information about a minor?", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input3, "IS_MINOR");
		
		section.addInput(input3);
	}
	
	
	private DynamicFormSection createSection2() {
		DynamicFormSection section2 = new DynamicFormSection();
		section2.setActive(true);
		section2.setPosition(2);
		createQuestion4(section2);
		createQuestion5(section2);
		createQuestion6(section2);
		createQuestion7(section2);
		createQuestion8(section2);
		createQuestion9(section2);
		createQuestion10(section2);
		section2.addLabel(new DynamicFormSectionLabel("El Contenido", ELanguages.SPANISH.getCode()));
		section2.addLabel(new DynamicFormSectionLabel("The Content", ELanguages.ENGLISH.getCode()));
		return section2;
	}

	private void createQuestion4(DynamicFormSection section) {
		DynamicFormInput input4 = new DynamicFormInput("IS_JUDICIAL_PROCEDEMENT", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 5);
		
		input4.addLabel(new DynamicFormInputLabel("¿El contenido está relacionado con un hecho sobre el que hay o ha habido un procedimiento judicial?", ELanguages.SPANISH.getCode()));
		input4.addLabel(new DynamicFormInputLabel("Is the content related to a fact about which there is or has been a judicial procedure?", ELanguages.ENGLISH.getCode()));
		
		input4.addDescription(new DynamicFormInputDescription("En caso afirmativo, adjuntar los documentos que lo respaldan en la sección de archivos.", ELanguages.SPANISH.getCode()));
		input4.addDescription(new DynamicFormInputDescription("If so, attach the supporting documents in the files section.", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input4, "IS_JUDICIAL_PROCEDEMENT");
		
		section.addInput(input4);
		
		DynamicFormInput input4_2 = new DynamicFormInput("IS_FAVORABLE_JUDGMENT", 
				"RADIO", 
				12, 
				false, "IS_JUDICIAL_PROCEDEMENT = IS_JUDICIAL_PROCEDEMENT_YES",
				false, "IS_JUDICIAL_PROCEDEMENT = IS_JUDICIAL_PROCEDEMENT_YES",
				true, null,
				null, null, 
				true, 6);
		
		input4_2.addLabel(new DynamicFormInputLabel("¿Fue sentencia firme favorable o auto de sobreseimiento?", ELanguages.SPANISH.getCode()));
		input4_2.addLabel(new DynamicFormInputLabel("Was it a favorable final judgment or a dismissal order?", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input4_2, "IS_FAVORABLE_JUDGMENT");
		
		section.addInput(input4_2);
		
		DynamicFormInput input4_2_1 = new DynamicFormInput("IS_JUDGMENT_DATE", 
				"DATE", 
				12, 
				false, "IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_YES",
				false, "IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_YES",
				true, null,
				null, null, 
				true, 7);
		
		input4_2_1.addLabel(new DynamicFormInputLabel("Fecha de la sentencia", ELanguages.SPANISH.getCode()));
		input4_2_1.addLabel(new DynamicFormInputLabel("Sentence date", ELanguages.ENGLISH.getCode()));
		
		section.addInput(input4_2_1);
		
		DynamicFormInput input4_2_2 = new DynamicFormInput("JUDGMENT_RESOLUTION", 
				"CHECKBOX", 
				12, 
				false, "IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_NO",
				false, "IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_NO",
				true, null,
				null, null, 
				true, 8);
		
		input4_2_2.addLabel(new DynamicFormInputLabel("Seleccione la resolución", ELanguages.SPANISH.getCode()));
		input4_2_2.addLabel(new DynamicFormInputLabel("Select resolution ", ELanguages.ENGLISH.getCode()));
		
		DynamicFormInputOption opt1 = new DynamicFormInputOption("SUSPENDED_CRIMINAL_RECORDS", true);
		opt1.addLabel(new DynamicFormInputOptionsLabel("Los antecedentes penales han sido suspendidos", ELanguages.SPANISH.getCode()));
		opt1.addLabel(new DynamicFormInputOptionsLabel("Criminal records have been suspended", ELanguages.ENGLISH.getCode()));
		
		DynamicFormInputOption opt2 = new DynamicFormInputOption("SUSPENDED_SENTENCE", true);
		opt2.addLabel(new DynamicFormInputOptionsLabel("La condena finalmente se suspendió, no ingresé a prisión", ELanguages.SPANISH.getCode()));
		opt2.addLabel(new DynamicFormInputOptionsLabel("The sentence was finally suspended, I did not enter prison", ELanguages.ENGLISH.getCode()));
		
		DynamicFormInputOption opt3 = new DynamicFormInputOption("SERVED_SENTENCE", true);
		opt3.addLabel(new DynamicFormInputOptionsLabel("He cumplido condena y zanjado mi deuda con la sociedad", ELanguages.SPANISH.getCode()));
		opt3.addLabel(new DynamicFormInputOptionsLabel("I have served my sentence and paid my debt to society", ELanguages.ENGLISH.getCode()));
		
		input4_2_2.addOption(opt1);
		input4_2_2.addOption(opt2);
		input4_2_2.addOption(opt3);
		
		section.addInput(input4_2_2);
	}

	private void createQuestion5(DynamicFormSection section) {
		DynamicFormInput input5 = new DynamicFormInput("IS_FAKE_INFO", 
				"RADIO", 
				12, 
				false, "!IS_JUDGMENT_DATE || IS_JUDGMENT_DATE",
				false, "!IS_JUDGMENT_DATE || IS_JUDGMENT_DATE",
				true, null,
				null, null, 
				true, 9);
		
		input5.addLabel(new DynamicFormInputLabel("¿La información es falsa?", ELanguages.SPANISH.getCode()));
		input5.addLabel(new DynamicFormInputLabel("Is the information false?", ELanguages.ENGLISH.getCode()));
		
		input5.addDescription(new DynamicFormInputDescription("En caso de ser afirmativo, adjuntar la sentencia en la sección de archivos e indicar la fecha de la sentencia.", ELanguages.SPANISH.getCode()));
		input5.addDescription(new DynamicFormInputDescription("If yes, attach the sentence in the files section and indicate the date of the sentence.", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input5, "IS_FAKE_INFO");
		
		section.addInput(input5);
	}

	private void createQuestion6(DynamicFormSection section) {
		DynamicFormInput input6 = new DynamicFormInput("IS_MORE_THAN_FOUR_YEARS", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 10);
		
		input6.addLabel(new DynamicFormInputLabel("¿Hace más de 4 años que se publicó el contenido?", ELanguages.SPANISH.getCode()));
		input6.addLabel(new DynamicFormInputLabel("Has the content been published more than 4 years ago?", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input6, "IS_MORE_THAN_FOUR_YEARS");
		
		section.addInput(input6);
		
		DynamicFormInput input6_1 = new DynamicFormInput("YEARS_CONTENTS", 
				"NUMBER", 
				12, 
				false, "IS_MORE_THAN_FOUR_YEARS = IS_MORE_THAN_FOUR_YEARS_YES",
				false, "IS_MORE_THAN_FOUR_YEARS = IS_MORE_THAN_FOUR_YEARS_YES",
				true, null,
				null, null, 
				true, 11);
		
		input6_1.addLabel(new DynamicFormInputLabel("¿Hace cuantos años se publicó el contenido?", ELanguages.SPANISH.getCode()));
		input6_1.addLabel(new DynamicFormInputLabel("How many years ago was the content published?", ELanguages.ENGLISH.getCode()));
		
		section.addInput(input6_1);
	}

	private void createQuestion8(DynamicFormSection section) {
		DynamicFormInput input7 = new DynamicFormInput("IS_SENSITIVE_DATA", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 12);
		
		input7.addLabel(new DynamicFormInputLabel("¿El contenido incluye datos sensibles personales?", ELanguages.SPANISH.getCode()));
		input7.addLabel(new DynamicFormInputLabel("Does the content include sensitive personal data?", ELanguages.ENGLISH.getCode()));
		
		input7.addDescription(new DynamicFormInputDescription("Un dato sensible puede ser un número telefónico, una dirección, estado de salud, sexual, racial, etc.", ELanguages.SPANISH.getCode()));
		input7.addDescription(new DynamicFormInputDescription("A sensitive data can be a telephone number, an address, health, sexual, racial, etc.", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input7, "IS_SENSITIVE_DATA");
		
		section.addInput(input7);
		
		DynamicFormInput input7_1 = new DynamicFormInput("SENSITIVE_DATA_TYPE", 
				"CHECKBOX", 
				12, 
				false, "IS_SENSITIVE_DATA = IS_SENSITIVE_DATA_YES",
				false, "IS_SENSITIVE_DATA = IS_SENSITIVE_DATA_YES",
				true, null,
				null, null, 
				true, 13);
		
		input7_1.addLabel(new DynamicFormInputLabel("¿A qué tipo de contenido hace referencia?", ELanguages.SPANISH.getCode()));
		input7_1.addLabel(new DynamicFormInputLabel("What type of content does it refer to? ", ELanguages.ENGLISH.getCode()));
		
		input7_1.addOption(createOption("SENSITIVE_PHONE", "Número telefónico", "Phone number"));
		input7_1.addOption(createOption("SENSITIVE_ADDRESS", "Dirección", "Address"));
		input7_1.addOption(createOption("SENSITIVE_HEALTH", "Salud", "Health"));
		input7_1.addOption(createOption("SENSITIVE_SEXUAL_LIFE", "Vida Sexual", "Sexual Life"));
		input7_1.addOption(createOption("SENSITIVE_RACIAL", "Origen Racial", "Racial Origin"));
		input7_1.addOption(createOption("SENSITIVE_ETNIA", "Origen Étnico", "Ethnic origin"));
		input7_1.addOption(createOption("SENSITIVE_RELIGION", "Creencias Religiosas", "Religious beliefs"));
		input7_1.addOption(createOption("SENSITIVE_POLITIC", "Ideología Política", "Politic ideology"));
		input7_1.addOption(createOption("SENSITIVE_PHILOSOPHIC", "Convicciones Filosóficas", "Philosophical convictions"));
		input7_1.addOption(createOption("SENSITIVE_TRADE_UNION", "Afiliación Sindical", "Trade Union Membership"));
		input7_1.addOption(createOption("SENSITIVE_GENETICS", "Genética", "Genetics"));
		
		section.addInput(input7_1);
	}

	private void createQuestion7(DynamicFormSection section) {
		DynamicFormInput input8 = new DynamicFormInput("CONTENT_FOCUS", 
				"SELECT", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 14);
		
		input8.addLabel(new DynamicFormInputLabel("¿El contenido es sobre tu vida privada o profesional?", ELanguages.SPANISH.getCode()));
		input8.addLabel(new DynamicFormInputLabel("Is the content about your private or professional life?", ELanguages.ENGLISH.getCode()));
		
		input8.addOption(createOption("PRIVATE", "Privada", "Private"));
		input8.addOption(createOption("PROFESIONAL", "Profesional", "Profesional"));
		
		section.addInput(input8);
	}

	private void createQuestion9(DynamicFormSection section) {
		DynamicFormInput input9 = new DynamicFormInput("IS_CYBER_BULLYING", 
				"RADIO", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 15);
		
		input9.addLabel(new DynamicFormInputLabel("¿Esta relacionado con cibeacoso, injurias y otros delitos contra ti?", ELanguages.SPANISH.getCode()));
		input9.addLabel(new DynamicFormInputLabel("Is it related to cyberbullying, insults and other crimes against you?", ELanguages.ENGLISH.getCode()));
		addRadioOptionsYesNo(input9, "IS_CYBER_BULLYING");
		
		section.addInput(input9);
	}

	private void createQuestion10(DynamicFormSection section) {
		DynamicFormInput input10 = new DynamicFormInput("AUTHOR_PUBLICATION", 
				"SELECT", 
				12, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, 16);
		
		input10.addLabel(new DynamicFormInputLabel("¿Quíen ha publicado el contenido?", ELanguages.SPANISH.getCode()));
		input10.addLabel(new DynamicFormInputLabel("Who has published the content?", ELanguages.ENGLISH.getCode()));
		
		DynamicFormInputOption opt1 = new DynamicFormInputOption("PUBLICATION_ME", true);
		opt1.addLabel(new DynamicFormInputOptionsLabel("Yo lo publique y ya no quiero que aparezca", ELanguages.SPANISH.getCode()));
		opt1.addLabel(new DynamicFormInputOptionsLabel("I published it and I no longer want it to appear", ELanguages.ENGLISH.getCode()));
		
		DynamicFormInputOption opt2 = new DynamicFormInputOption("PUBLICATION_OTHER", true);
		opt2.addLabel(new DynamicFormInputOptionsLabel("Lo ha publicado otra persona", ELanguages.SPANISH.getCode()));
		opt2.addLabel(new DynamicFormInputOptionsLabel("Posted by someone else", ELanguages.ENGLISH.getCode()));
		
		input10.addOption(opt1);
		input10.addOption(opt2);
		
		section.addInput(input10);
	}
	

	private DynamicFormInputOption createOption(String name, String tagES, String tagEN) {
		DynamicFormInputOption opt = new DynamicFormInputOption(name, true);
		opt.addLabel(new DynamicFormInputOptionsLabel(tagES, ELanguages.SPANISH.getCode()));
		opt.addLabel(new DynamicFormInputOptionsLabel(tagEN, ELanguages.ENGLISH.getCode()));
		return opt;
	}

	
	private void createGoogleResults(DynamicForm form) {
		// ENCABEZADOS
		List<DynamicFormConditionResult> googleResults = Arrays.asList(
				createConditonResult(
						"TRUE", 
						"(1) Contiene información sobre mí. (2)", 
						""),
				createConditonResult(
						"TRUE", 
						"La disponibilidad en el motor de búsqueda de la URL tiene un impacto desproporcionado negativo en mi reputación.", 
						""),
				createConditonResult(
						"TRUE", 
						"Por que aparece como resultado de la búsqueda realizada a partir de mi nombre, exclusivamente.", 
						""),
				createConditonResult(
						"TRUE", 
						"La información contiene comentarios personales desagradables y de una campaña personal contra mí.", 
						""),
				createConditonResult(
						"IS_NOTORIOUS = IS_NOTORIOUS_NO", 
						"Porque no soy un “personaje público”.", 
						""),
				createConditonResult(
						"IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_YES", 
						"Porque hay una sentencia firme absolutoria por tanto resulta inadecuada, inexacta y no pertinente.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SERVED_SENTENCE", 
						"Los antecedentes penales han sido suspendidos.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SUSPENDED_CRIMINAL_RECORDS", 
						"La condena finalmente se suspendió, no ingrese en prisión.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SUSPENDED_SENTENCE", 
						"La pena que se me impuso fue objeto de suspensión.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SERVED_SENTENCE", 
						"Pendiente (Pendiente de duda)", 
						""),
				createConditonResult(
						"IS_FAKE_INFO = IS_FAKE_INFO_YES AND IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_YES", 
						"Son datos e informaciones relativo a acusaciones probadamente falsas en virtud de la sentencia emitida.", 
						""),
				createConditonResult(
						"IS_FAKE_INFO = IS_FAKE_INFO_YES AND IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_NO", 
						"Son datos e informaciones relativo a acusaciones probadamente falsas como acredita la documentación anexa a esta solicitud.", 
						""),
				createConditonResult(
						"IS_MORE_THAN_FOUR_YEARS = IS_MORE_THAN_FOUR_YEARS_YES", 
						"Porque la información contenida en la referida URL ha devenido desactualizada y/o excesiva por el transcurso del tiempo, dado que se publicó hace {{YEARS_CONTENTS}} años.", 
						""),
				createConditonResult(
						"IS_MINOR = IS_MINOR_YES", 
						"La información contenida en la referida URL se refiere a un menor de edad.", 
						""),
				createConditonResult(
						"IS_SENSITIVE_DATA = IS_SENSITIVE_DATA_YES", 
						"La URL contiene “información o datos sensibles” sobre mí, de acuerdo con el artículo 8 de la Directiva 95/546/CE y el artículo 9 de Ley Orgánica 3/2018; en concreto, contiene datos relativos a mi {{SENSITIVE_DATA_TYPE}}, que afectan en mayor medida a mi vida personal que otras categorías de datos.", 
						""),
				createConditonResult(
						"CONTENT_FOCUS = PRIVATE", 
						"La información de la URL está relacionada con mi vida personal y no profesional.", 
						""),
				createConditonResult(
						"IS_CYBER_BULLYING = IS_CYBER_BULLYING_YES", 
						"La información contenida en la referida URL podría resultar constitutiva de un delito de injuria, ciberacoso y/o otros delitos similares, por lo que será puesta en conocimiento de la policía y los tribunales.", 
						""),
				createConditonResult(
						"AUTHOR_PUBLICATION = PUBLICATION_ME", 
						"La información contenida en la referida URL se publicó inicialmente con mi consentimiento. No obstante, he revocado de forma expresa dicho consentimiento. Las Directrices son tajantes en casos como el presente.", 
						""),
				createConditonResult(
						"AUTHOR_PUBLICATION = PUBLICATION_OTHER", 
						"Resulta especialmente grave y relevante a los presentes efectos que en ningún momento he hecho públicos tales datos.", 
						"")
				);
		
		addConditions("GOOGLE", form, googleResults);
	}

	private void createMediaResults(DynamicForm form) {
		List<DynamicFormConditionResult> mediaResults = Arrays.asList(
				createConditonResult(
						"TRUE", 
						"Por medio del presente escrito ejerzo el derecho de supresión, de conformidad con lo previsto en el artículo 17 del Reglamento UE 2016/679, General de Protección de Datos (“RGPD”), las Directrices sobre la ejecución de la Sentencia del Tribunal de Justicia de la Unión Europea en el asunto “Google Spain and Inc v. Agencia Española de Protección de Datos (AEPD) y Mario Costeja González c-131/12”, del Grupo de Trabajo de Protección de datos del artículo 29 (las “Directrices”), y el artículo 93 de la Ley Orgánica 3/2018, de 5 de diciembre, de Protección de Datos Personales y garantía de los derechos digitales (“LOPD”). Esta última norma, en su apartado primero, recoge lo siguiente:          “toda persona tiene derecho a que los motores de búsqueda en Internet eliminen de las listas de resultados que se obtuvieran tras una búsqueda efectuada a partir de su nombre los enlaces publicados que contuvieran información relativa a esa persona cuando fuesen inadecuados, inexactos, no pertinentes, no actualizados o excesivos o hubieren devenido como tales por el transcurso del tiempo, teniendo en cuenta los fines para los que se recogieron o trataron, el tiempo transcurrido y la naturaleza e interés público de la información.", 
						""),
				createConditonResult(
						"TRUE", 
						"(1)  La URL está relacionada conmigo porque contiene información sobre mí. (2)", 
						""),
				createConditonResult(
						"TRUE", 
						"Porque la disponibilidad en el motor de búsqueda de la información contenida en la referida URL me provoca un grave perjuicio y daño reputacional. En palabras de las Directrices, la disponibilidad de la referida información tiene un impacto desproporcionadamente negativo en mi reputación e imagen, ya que no suscita –ni ha suscitado nunca– debate público, y no genera interés público de ningún tipo. ", 
						""),
				createConditonResult(
						"TRUE", 
						"Porque la información contenida en la referida URL aparece como resultado de la búsqueda realizada a partir de mi nombre, sin incluir ninguna palabra distinta de mi nombre y apellidos. Ello tiene un grave impacto particular en mi derecho a que se respete mi vida privada, tal y como se reconoce en las Directrices. La Sentencia declara lo siguiente sobre esta cuestión:", 
						""),
				createConditonResult(
						"TRUE", 
						"“(…) al analizar los requisitos de aplicación de estas disposiciones, se tendrá que examinar, en particular, si el interesado tiene derecho a que la información en cuestión relativa a su persona ya no esté, en la situación actual, vinculada a su nombre por una lista de resultados, obtenida tras una búsqueda efectuada a partir de su nombre, sin que la apreciación de la existencia de tal derecho presuponga que la inclusión de la información en cuestión en la lista de resultados cause un perjuicio al interesado. Puesto que éste puede, habida cuenta de los derechos que le reconocen los artículos 7 y 8 de la Carta, solicitar que la información de que se trate ya no se ponga a disposición del público en general mediante su inclusión en tal lista de resultados, estos derechos prevalecen, en principio, no sólo sobre el interés económico del gestor del motor de búsqueda, sino también sobre el interés de dicho público en acceder a la mencionada información en una búsqueda que verse sobre el nombre de esa persona. Sin embargo, tal no sería el caso si resultara, por razones concretas, como el papel desempeñado por el interesado en la vida pública, que la injerencia en sus derechos fundamentales está justificada por el interés preponderante de dicho público en tener, a raíz de esta inclusión, acceso a la información de que se trate”.", 
						""),
				createConditonResult(
						"TRUE", 
						"La información contenida en la referida URL resulta inadecuada, inexacta y no pertinente, ya que contiene comentarios personales desagradables y/o forma parte de una campaña personal contra mí, lo que resulta de especial relevancia tal y como se reconoce en las Directrices.", 
						""),
				createConditonResult(
						"IS_NOTORIOUS = IS_NOTORIOUS_YES", 
						"Porque no soy un “personaje público”, de acuerdo con la definición dada por la Resolución 1165 (1998) de la Asamblea Parlamentaria del Consejo de Europa: “personas que ejercen funciones públicas y/o utilizan recursos públicos y, de una forma más general, todas aquellas que desempeñan un papel en la vida pública, bien político, económico, artístico, social, deportivo u otro”. Por tanto, no me resulta de aplicación la excepción al derecho al olvido por interés público, establecida por el Tribunal de Justicia de la Unión Europea.", 
						""),
				createConditonResult(
						"IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_YES", 
						"Porque en fecha {{IS_JUDGMENT_DATE}} ha recaído sentencia firme absolutoria/auto firme de sobreseimiento. Por tanto, la información contenida en la referida URL, al menos en lo que se refiere a mi persona, resulta inadecuada, inexacta y no pertinente, y, en definitiva, “ya no tiene una actualidad razonable y resulta inexacta por no estar actualizada”, en palabras de las Directrices, al referirse a un procedimiento judicial que ya ha finalizado.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SERVED_SENTENCE", 
						"Resulta especialmente relevante que, ex artículo 136 del Código Penal, que los antecedentes penales han sido suspendidos.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SUSPENDED_CRIMINAL_RECORDS", 
						"La condena finalmente se suspendió, no ingrese en prisión.", 
						""),
				createConditonResult(
						"JUDGMENT_RESOLUTION = SUSPENDED_SENTENCE", 
						"La pena que se me impuso fue objeto de suspensión.", 
						""),
				createConditonResult(
						"IS_FAKE_INFO = IS_FAKE_INFO_YES AND IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_YES", 
						"Se trata de contenido (datos e informaciones) relativo a acusaciones probadamente falsas en virtud de la sentencia número [número], de fecha [fecha] del Juzgado [número] de [Ciudad].\n Además, la información contenida en la referida URL resulta inadecuada, inexacta y no pertinente.", 
						""),
				createConditonResult(
						"IS_FAKE_INFO = IS_FAKE_INFO_YES AND IS_FAVORABLE_JUDGMENT = IS_FAVORABLE_JUDGMENT_NO", 
						"Se trata de contenido (datos e informaciones) relativo a acusaciones probadamente falsas como acredita la documentación anexa a esta solicitud.", 
						""),
				createConditonResult(
						"IS_MORE_THAN_FOUR_YEARS = IS_MORE_THAN_FOUR_YEARS_YES", 
						"La información contenida en la referida URL “ya no tiene una actualidad razonable”, en palabras de las Directrices, al referirse a unos hechos ocurridos hace muchos años.\n Por ello, la información contenida en la referida URL ha devenido desactualizada y/o excesiva por el transcurso del tiempo. Tal y como se expone en las Directrices, “la relevancia está además estrechamente relacionada con la antigüedad de los datos.\"", 
						""),
				createConditonResult(
						"IS_MINOR = IS_MINOR_YES", 
						"La información contenida en la referida URL se refiere a un menor de edad. Ello resulta trascendental, y según las Directrices:\n “Las APD han de tener en cuenta el criterio del «interés superior del menor». Tal concepto se recoge, entre otros textos, en el artículo 24 de la Carta de los Derechos Fundamentales de la UE. «En todos los actos relativos a los menores llevados a cabo por autoridades públicas o instituciones privadas, el interés superior del menor constituirá una consideración primordial”.", 
						""),
				createConditonResult(
						"IS_SENSITIVE_DATA = IS_SENSITIVE_DATA_YES", 
						"La URL contiene “información o datos sensibles” sobre mí, de acuerdo con el artículo 8 de la Directiva 95/546/CE y el artículo 9 de Ley Orgánica 3/2018; en concreto, contiene datos relativos a mi {{SENSITIVE_DATA_TYPE}}, que afectan en mayor medida a mi vida personal que otras categorías de datos.", 
						""),
				createConditonResult(
						"CONTENT_FOCUS = PRIVATE", 
						"Porque la información contenida en la referida URL está relacionada con mi vida personal y no profesional, por lo que mi derecho fundamental a la intimidad (y a la protección de datos) merece especial protección en este caso, y refuerza la falta o pérdida de interés de la información que solicito retirar.", 
						""),
				createConditonResult(
						"IS_CYBER_BULLYING = IS_CYBER_BULLYING_YES", 
						"La información contenida en la referida URL podría resultar constitutiva de un delito de injuria, ciberacoso y/o otros delitos similares, por lo que será puesta en conocimiento de la policía y los tribunales.", 
						""),
				createConditonResult(
						"AUTHOR_PUBLICATION = PUBLICATION_ME", 
						"La información contenida en la referida URL se publicó inicialmente con mi consentimiento. No obstante, he revocado de forma expresa dicho consentimiento. Las Directrices son tajantes en casos como el presente:\n “Si el único fundamento jurídico de la disponibilidad en Internet de datos personales es el consentimiento del interesado y este revoca luego el consentimiento, entonces la actividad de tratamiento - es decir, la publicación - carece de fundamento y debe cesar”.", 
						""),
				createConditonResult(
						"AUTHOR_PUBLICATION = PUBLICATION_OTHER", 
						"Resulta especialmente grave y relevante a los presentes efectos que en ningún momento he hecho públicos tales datos.", 
						"")			
				);
		
		addConditions("MEDIA", form, mediaResults);
	}
	
	private void addConditions(String type, DynamicForm form, List<DynamicFormConditionResult> list) {
		if (list != null) {
			int i = 1;
			for (DynamicFormConditionResult condition : list) { 
				condition.setPosition(i);
				condition.setType(type);
				form.addCondition(condition);
				i++;
			}
		}
	}
	
	private DynamicFormConditionResult createConditonResult(String condition, String textES, String textEN) {
		DynamicFormConditionResult result = new DynamicFormConditionResult();
		result.setActive(true);
		result.setCondition(condition);
		result.addLabel(new DynamicFormConditionResultLabel(textES, ELanguages.SPANISH.getCode()));
		result.addLabel(new DynamicFormConditionResultLabel(textEN, ELanguages.ENGLISH.getCode()));
		return result;
	}
	
	private void addRadioOptionsYesNo(DynamicFormInput input, String name) {
		DynamicFormInputOption opt1 = new DynamicFormInputOption(name+"_YES", true);
		opt1.addLabel(new DynamicFormInputOptionsLabel("Si", ELanguages.SPANISH.getCode()));
		opt1.addLabel(new DynamicFormInputOptionsLabel("Yes", ELanguages.ENGLISH.getCode()));

		input.addOption(opt1);
		
		DynamicFormInputOption opt2 = new DynamicFormInputOption(name+"_NO", true);
		opt2.addLabel(new DynamicFormInputOptionsLabel("No", ELanguages.SPANISH.getCode()));
		opt2.addLabel(new DynamicFormInputOptionsLabel("No", ELanguages.ENGLISH.getCode()));
		input.addOption(opt2);
	}
}
