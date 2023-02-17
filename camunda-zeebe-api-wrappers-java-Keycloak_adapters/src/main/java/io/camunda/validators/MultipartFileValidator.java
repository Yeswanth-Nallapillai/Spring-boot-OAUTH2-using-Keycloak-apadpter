package io.camunda.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import io.camunda.annotations.ValidBPMNFile;

public class MultipartFileValidator implements ConstraintValidator<ValidBPMNFile, MultipartFile> {

	private static final String BPMN_EXTENSION = "bpmn";
	private static final Logger LOGGER = LoggerFactory.getLogger(MultipartFileValidator.class);

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		LOGGER.info(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
		return BPMN_EXTENSION.equalsIgnoreCase(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
	}

}
