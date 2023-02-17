package io.camunda.controllers.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import io.camunda.controllers.FormsController;
import io.camunda.operate.exception.OperateException;
import io.camunda.services.BpmnService;
import io.camunda.utils.JsonUtils;

@CrossOrigin
@RestController
public class FormsControllerImpl implements FormsController {

	@Autowired
	private BpmnService bpmnService;

	public JsonNode getFormSchema(String processName, String processDefinitionId, String formKey)
			throws IOException, NumberFormatException, OperateException {
		JsonNode formSchema = null;
		if (formKey.startsWith("camunda-forms:bpmn:")) {
			String formId = formKey.substring(formKey.lastIndexOf(":") + 1);
			String schema = bpmnService.getEmbeddedFormSchema(processName, processDefinitionId, formId);
			formSchema = JsonUtils.toJsonNode(schema);
			return formSchema;
		}
		return formSchema;
	}

}
