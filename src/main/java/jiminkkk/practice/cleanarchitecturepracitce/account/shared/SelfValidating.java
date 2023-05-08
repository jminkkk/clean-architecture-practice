package jiminkkk.practice.cleanarchitecturepracitce.account.shared;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T> {
    private Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Bean Vaildation을 검증하고 유효성 검증 규칙을 위반한 경우 예외를 던짐
    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if(!violations.isEmpty()) throw new IllegalArgumentException((Throwable) violations);
    }

}
