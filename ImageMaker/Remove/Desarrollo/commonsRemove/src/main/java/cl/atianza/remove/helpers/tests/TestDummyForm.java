package cl.atianza.remove.helpers.tests;

import java.util.ArrayList;
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

public class TestDummyForm {
	public void createDummyVersion() throws RemoveApplicationException {
		DynamicFormDao.init().deactiveAll();
		DynamicForm form = new DynamicForm();
		
		form.setActive(true);
		form.setType(EDynamicFormTypes.DEINDEXATION.getTag());
		form.setCreation_date(RemoveDateUtils.nowLocalDateTime());
		form.setVersion("0.0");
		form.setSections(createDummySections());
		
		createGoogleResults(form);
		
		form = DynamicFormDao.init().save(form);
		DynamicFormDao.init().active(form);
	}

	private List<DynamicFormSection> createDummySections() {
		List<DynamicFormSection> lst = new ArrayList<DynamicFormSection>();
		
		lst.add(createSectionDummy12());
		lst.add(createSectionDummy6());
		lst.add(createSectionDummy3());
		lst.add(createSectionDummyExtra());
		
		return lst;
	}

	private DynamicFormSection createSectionDummyExtra() {
		DynamicFormSection sectionExtra = new DynamicFormSection();
		sectionExtra.setActive(true);
		sectionExtra.setPosition(3);
		
		sectionExtra.addInput(createInputText("TEXT_EXTRA", 1, 4, "Texto espacio EXTRA", "Input espacio EXTRA EN"));
		sectionExtra.addInput(createInputNumber("NUMBER_EXTRA", 2, 4, "Números espacio 3", "Números espacio 3 EN"));
		sectionExtra.addInput(createInputDate("DATE_3", 3, 4, "Fecha espacio EXTRA", "Fecha espacio EXTRA EN"));
		sectionExtra.addInput(createInputSelect("SELECT_EXTRA", 4, 4, "Select espacio EXTRA", "Select espacio EXTRA EN", buildGenericOptions("SEL_EXTRA_")));
		sectionExtra.addInput(createInputRatio("RADIO_EXTRA", 5, 8, "Radio espacio EXTRA", "Radio espacio EXTRA EN", buildGenericOptions("RAD_EXTRA_")));
		sectionExtra.addInput(createInputCheckbox("CHECKBOX_EXTRA", 6, 12, "Checkbox espacio EXTRA", "Checkbox espacio EXTRA EN", buildGenericOptions("CHE_EXTRA_")));
		sectionExtra.addInput(createInputTextArea("TEXTAREA_EXTRA", 7, 12, "Textarea espacio EXTRA", "Textarea espacio EXTRA EN"));
		sectionExtra.addInput(createInputSwitch("SWITCH_EXTRA", 8, 6, "Switch espacio EXTRA", "Switch espacio EXTRA EN"));
		
		sectionExtra.addLabel(new DynamicFormSectionLabel("Sección Espacios Extra", ELanguages.SPANISH.getCode()));
		sectionExtra.addLabel(new DynamicFormSectionLabel("Sección Espacios Extra", ELanguages.ENGLISH.getCode()));
		
		return sectionExtra;
	}

	private DynamicFormSection createSectionDummy3() {
		DynamicFormSection section3 = new DynamicFormSection();
		section3.setActive(true);
		section3.setPosition(3);
		
		section3.addInput(createInputText("TEXT_3", 1, 3, "Texto espacio 3", "Input espacio 3 EN"));
		section3.addInput(createInputNumber("NUMBER_3", 2, 3, "Números espacio 3", "Números espacio 3 EN"));
		section3.addInput(createInputDate("DATE_3", 3, 3, "Fecha espacio 3", "Fecha espacio 3 EN"));
		section3.addInput(createInputSelect("SELECT_3", 4, 3, "Select espacio 3", "Select espacio 3 EN", buildGenericOptions("SEL_3_")));
		section3.addInput(createInputRatio("RADIO_3", 5, 3, "Radio espacio 3", "Radio espacio 3 EN", buildGenericOptions("RAD_3_")));
		section3.addInput(createInputCheckbox("CHECKBOX_3", 6, 3, "Checkbox espacio 3", "Checkbox espacio 3 EN", buildGenericOptions("CHE_3_")));
		section3.addInput(createInputTextArea("TEXTAREA_3", 7, 3, "Textarea espacio 3", "Textarea espacio 3 EN"));
		section3.addInput(createInputSwitch("SWITCH_3", 8, 3, "Switch espacio 3", "Switch espacio 3 EN"));
		
		section3.addLabel(new DynamicFormSectionLabel("Sección Espacios 3", ELanguages.SPANISH.getCode()));
		section3.addLabel(new DynamicFormSectionLabel("Sección Espacios 3", ELanguages.ENGLISH.getCode()));
		
		return section3;
	}

	private DynamicFormSection createSectionDummy6() {
		DynamicFormSection section6 = new DynamicFormSection();
		section6.setActive(true);
		section6.setPosition(2);
		
		section6.addInput(createInputText("TEXT_6", 1, 6, "Texto espacio 6", "Input espacio 6 EN"));
		section6.addInput(createInputNumber("NUMBER_6", 2, 6, "Números espacio 6", "Números espacio 6 EN"));
		section6.addInput(createInputDate("DATE_6", 3, 6, "Fecha espacio 6", "Fecha espacio 6 EN"));
		section6.addInput(createInputSelect("SELECT_6", 4, 6, "Select espacio 6", "Select espacio 6 EN", buildGenericOptions("SEL_6_")));
		section6.addInput(createInputRatio("RADIO_6", 5, 6, "Radio espacio 6", "Radio espacio 6 EN", buildGenericOptions("RAD_6_")));
		section6.addInput(createInputCheckbox("CHECKBOX_6", 6, 6, "Checkbox espacio 6", "Checkbox espacio 6 EN", buildGenericOptions("CHE_6_")));
		section6.addInput(createInputTextArea("TEXTAREA_6", 7, 6, "Textarea espacio 6", "Textarea espacio 6 EN"));
		section6.addInput(createInputSwitch("SWITCH_6", 8, 6, "Switch espacio 6", "Switch espacio 6 EN"));
		
		section6.addLabel(new DynamicFormSectionLabel("Sección Espacios 6", ELanguages.SPANISH.getCode()));
		section6.addLabel(new DynamicFormSectionLabel("Sección Espacios 6", ELanguages.ENGLISH.getCode()));
		
		return section6;
	}

	private DynamicFormSection createSectionDummy12() {
		DynamicFormSection section12 = new DynamicFormSection();
		section12.setActive(true);
		section12.setPosition(1);
		
		section12.addInput(createInputText("TEXT_12", 1, 12, "Texto espacio 12", "Input espacio 12 EN"));
		section12.addInput(createInputNumber("NUMBER_12", 2, 12, "Números espacio 12", "Números espacio 12 EN"));
		section12.addInput(createInputDate("DATE_12", 3, 12, "Fecha espacio 12", "Fecha espacio 12 EN"));
		section12.addInput(createInputSelect("SELECT_12", 4, 12, "Select espacio 12", "Select espacio 12 EN", buildGenericOptions("SEL_12_")));
		section12.addInput(createInputRatio("RADIO_12", 5, 12, "Radio espacio 12", "Radio espacio 12 EN", buildGenericOptions("RAD_12_")));
		section12.addInput(createInputCheckbox("CHECKBOX_12", 6, 12, "Checkbox espacio 12", "Checkbox espacio 12 EN", buildGenericOptions("CHE_12_")));
		section12.addInput(createInputTextArea("TEXTAREA_12", 7, 12, "Textarea espacio 12", "Textarea espacio 12 EN"));
		section12.addInput(createInputSwitch("SWITCH_12", 8, 12, "Switch espacio 12", "Switch espacio 12 EN"));
		
		section12.addLabel(new DynamicFormSectionLabel("Sección Espacios 12", ELanguages.SPANISH.getCode()));
		section12.addLabel(new DynamicFormSectionLabel("Sección Espacios 12", ELanguages.ENGLISH.getCode()));
		
		return section12;
	}
	
	private List<DynamicFormInputOption> buildGenericOptions(String name) {
		List<DynamicFormInputOption> list = new ArrayList<DynamicFormInputOption>();
		
		DynamicFormInputOption opt1 = new DynamicFormInputOption(name+"OPTION_1", true);
		opt1.addLabel(new DynamicFormInputOptionsLabel("Opcion 1", ELanguages.SPANISH.getCode()));
		opt1.addLabel(new DynamicFormInputOptionsLabel("Option 1", ELanguages.ENGLISH.getCode()));
		list.add(opt1);
		
		DynamicFormInputOption opt2 = new DynamicFormInputOption(name+"OPTION_2", true);
		opt2.addLabel(new DynamicFormInputOptionsLabel("Opcion 2", ELanguages.SPANISH.getCode()));
		opt2.addLabel(new DynamicFormInputOptionsLabel("Option 2", ELanguages.ENGLISH.getCode()));
		list.add(opt2);
		
		DynamicFormInputOption opt3 = new DynamicFormInputOption(name+"OPTION_3", true);
		opt3.addLabel(new DynamicFormInputOptionsLabel("Opcion 3", ELanguages.SPANISH.getCode()));
		opt3.addLabel(new DynamicFormInputOptionsLabel("Option 3", ELanguages.ENGLISH.getCode()));
		list.add(opt3);
		
		return list;
	}

	private void createGoogleResults(DynamicForm form) {
		// ENCABEZADOS
		DynamicFormConditionResult cond1 = new DynamicFormConditionResult("GOOGLE", 1, "TRUE", true);
		cond1.addLabel(new DynamicFormConditionResultLabel("Este resultado va fijo. \n", ELanguages.SPANISH.getCode()));
		cond1.addLabel(new DynamicFormConditionResultLabel("Este resultado va fijo. \n", ELanguages.ENGLISH.getCode()));
		form.addCondition(cond1);
		
		DynamicFormConditionResult cond2 = new DynamicFormConditionResult("GOOGLE", 2, "NUMBER_12 = 10", true);
		cond2.addLabel(new DynamicFormConditionResultLabel("Este va si {{NUMBER_12}} es igual a 10. \n", ELanguages.SPANISH.getCode()) );
		cond2.addLabel(new DynamicFormConditionResultLabel("Este va si {{NUMBER_12}} es igual a 10. \n", ELanguages.ENGLISH.getCode()));
		form.addCondition(cond2);
		
		DynamicFormConditionResult cond3 = new DynamicFormConditionResult("GOOGLE", 3, "FALSE", true);
		cond3.addLabel(new DynamicFormConditionResultLabel("Este nunca va... ", ELanguages.SPANISH.getCode()));
		cond3.addLabel(new DynamicFormConditionResultLabel("Este nunca va... ", ELanguages.ENGLISH.getCode()));
		form.addCondition(cond3);
	}
	
	private DynamicFormInput createInputTextArea(String name, Integer position, Integer width, String labelEs, String labelEn) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"TEXT_AREA", 
				width, 
				false, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));
		
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		return input;
	}

	private DynamicFormInput createInputText(String name, Integer position, Integer width, String labelEs, String labelEn) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"TEXT", 
				width, 
				false, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		return input;
	}
	
	private DynamicFormInput createInputNumber(String name, Integer position, Integer width, String labelEs, String labelEn) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"NUMBER", 
				width, 
				false, null,
				true, "(RADIO_TEST=OPTION_9 AND CHECKBOX_TEST=OPTION_6) OR SELECT_TEST=OPTION_2",
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		return input;
	}
	
	private DynamicFormInput createInputDate(String name, Integer position, Integer width, String labelEs, String labelEn) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"DATE", 
				width, 
				false, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		return input;
	}

	private DynamicFormInput createInputSelect(String name, Integer position, Integer width, String labelEs, String labelEn, List<DynamicFormInputOption> options) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"SELECT", 
				width, 
				false, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		input.setOptions(options);
	
		return input;
	}

	private DynamicFormInput createInputCheckbox(String name, Integer position, Integer width, String labelEs, String labelEn, List<DynamicFormInputOption> options) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"CHECKBOX", 
				width, 
				false, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		input.setOptions(options);

		return input;
	}

	private DynamicFormInput createInputRatio(String name, Integer position, Integer width, String labelEs, String labelEn, List<DynamicFormInputOption> options) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"RADIO", 
				width, 
				false, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		input.setOptions(options);
		
		return input;
	}

	private DynamicFormInput createInputSwitch(String name, Integer position, Integer width, String labelEs, String labelEn) {
		DynamicFormInput input = new DynamicFormInput(name, 
				"SWITCH", 
				width, 
				true, null,
				true, null,
				true, null,
				null, null, 
				true, position);
		
		input.addLabel(new DynamicFormInputLabel(labelEs, ELanguages.SPANISH.getCode()));
		input.addLabel(new DynamicFormInputLabel(labelEn, ELanguages.ENGLISH.getCode()));

		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.SPANISH.getCode()));
		input.addDescription(new DynamicFormInputDescription("Lorem Ipsum, Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit......", ELanguages.ENGLISH.getCode()));
		
		
		return input;
	}

//	private List<DynamicFormConditionResult> createTestConditionResults() {
//		List<DynamicFormConditionResult> lst = new ArrayList<DynamicFormConditionResult>();
//		
//		DynamicFormConditionResult cond1 = new DynamicFormConditionResult(1, "(RADIO_TEST = OPTION_1 AND SELECT_TEST = OPTION_3) OR CHECKBOX_TEST = OPTION_5", true);
//		cond1.addLabel(new DynamicFormConditionResultLabel("Se ha llegado al resultado 1", ELanguages.SPANISH.getCode()));
//		cond1.addLabel(new DynamicFormConditionResultLabel("Result 1 has been reached", ELanguages.SPANISH.getCode()));
//		
//		DynamicFormConditionResult cond2 = new DynamicFormConditionResult(2, "SELECT_TEST = OPTION_1", true);
//		cond2.addLabel(new DynamicFormConditionResultLabel("Se ha llegado al resultado 2", ELanguages.SPANISH.getCode()));
//		cond2.addLabel(new DynamicFormConditionResultLabel("Result 2 has been reached", ELanguages.SPANISH.getCode()));
//		
//		DynamicFormConditionResult cond3 = new DynamicFormConditionResult(3, "TEXT_TEST = 'HOLA'", true);
//		cond3.addLabel(new DynamicFormConditionResultLabel("Se ha llegado al resultado 3", ELanguages.SPANISH.getCode()));
//		cond3.addLabel(new DynamicFormConditionResultLabel("Result 3 has been reached", ELanguages.SPANISH.getCode()));
//		
//		lst.add(cond1);
//		lst.add(cond2);
//		lst.add(cond3);
//		
//		return lst;
//	}
	
	public static void main(String[] args) {
		try {
			// new TestDeindexationFormCreator().createVersionOne();
			new TestDeindexationFormCreator2().createVersionOneDotOne();
		} catch (RemoveApplicationException e) {
			e.printStackTrace();
		}
	}
}
