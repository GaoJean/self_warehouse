package com.warehouse.common.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

// @Aspect
@Configuration
//@EnableAspectJAutoProxy
public class ValidatorAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidatorAspect.class);

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
            // failFast的意思只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
            .failFast(true).buildValidatorFactory();
        Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }
    //
    // @Pointcut("execution(* com.jeangoo.frame.service.core.api.api..*.*(@javax.validation.Valid (*),
    // ..))")
    // public void pointCut() {
    // }
    //
    // @Around("pointCut()")
    // public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    // try {
    // return proceedingJoinPoint.proceed();
    // } catch (ConstraintViolationException e) {
    // String msg = e.getConstraintViolations().stream().filter(Objects::nonNull)
    // .map(ConstraintViolation::getMessage).filter(StringUtils::hasText).findFirst().orElse("未知错误");
    // LOGGER.info("Validate error: {}", msg);
    // throw new BusinessException(XxxErrorCode.DYNAMIC_PARAM_ERROR, msg);
    // }
    // }
}
