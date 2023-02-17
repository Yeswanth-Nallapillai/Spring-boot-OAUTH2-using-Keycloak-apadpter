package io.camunda.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;

import io.camunda.annotations.OpenAPI;
import io.camunda.constants.Constants;
import io.camunda.operate.exception.OperateException;

public interface FormsController extends VersionController{

	@GetMapping("getFormData/{processName}/{processDefinitionId}/{formKey}")
	@OpenAPI(summary = "", name = Constants.FORMS)
	@ResponseBody
	public JsonNode getFormSchema(@PathVariable String processName, @PathVariable String processDefinitionId,
			@PathVariable String formKey) throws IOException, NumberFormatException, OperateException;

}
