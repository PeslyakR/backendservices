package com.services.app.services.support;

import com.services.app.exceptions.ServerException;

public abstract class CommonItems {

    public static void checkTitle(String value) throws ServerException {
        if (value == null || value.trim().length() == 0) {
            System.out.println("asdasd");
            throw new ServerException("Поле обязательно для заполнения.");
        }
    }

}
