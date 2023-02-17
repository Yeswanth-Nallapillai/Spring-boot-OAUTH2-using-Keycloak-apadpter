package io.camunda.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import io.camunda.operate.exception.OperateException;
import io.camunda.services.BpmnService;
import io.camunda.services.OperateService;
import io.camunda.utils.BpmnUtils;

@Service
@EnableCaching
public class BpmnServiceImpl implements BpmnService {

	public static final String BPMN_EXTENSION = ".bpmn";

	@Autowired
	private OperateService operateService;

	@Cacheable("processEmbeddedForms")
	public String getEmbeddedFormSchema(String bpmnProcessId, String processDefinitionId, String formId)
			throws NumberFormatException, OperateException {
		if (bpmnProcessId != null) {
			String schema = BpmnUtils.getFormSchemaFromFile(bpmnProcessId + BPMN_EXTENSION, formId);

			if (schema != null) {
				return schema;
			}
		}
		String xml = operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
		return BpmnUtils.getFormSchemaFromBpmn(xml, formId);
	}

	@Cacheable("processDefinitionXml")
	public String getProcessDefinitionXml(String processDefinitionId) throws NumberFormatException, OperateException {
		return operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
	}

	@Cacheable("processNames")
	public String getProcessName(String bpmnProcessId, String processDefinitionId)
			throws NumberFormatException, OperateException {

		if (bpmnProcessId != null) {
			return BpmnUtils.getProcessNameFromFile(bpmnProcessId + BPMN_EXTENSION, bpmnProcessId);
		}
		String xml = operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
		return BpmnUtils.getTaskNameFromBpmn(xml, bpmnProcessId);
	}

	@Cacheable("processTaskNames")
	public String getTaskName(String bpmnProcessId, String processDefinitionId, String activityId)
			throws NumberFormatException, OperateException {

		if (bpmnProcessId != null) {
			return BpmnUtils.getTaskNameFromFile(bpmnProcessId + BPMN_EXTENSION, activityId);
		}
		String xml = operateService.getProcessDefinitionXmlByKey(Long.valueOf(processDefinitionId));
		return BpmnUtils.getTaskNameFromBpmn(xml, activityId);
	}
}
