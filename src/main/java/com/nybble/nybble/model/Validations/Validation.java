package com.nybble.nybble.model.Validations;

import java.util.Optional;

public interface Validation {
    public Optional<ValidationObject> validate();
}
