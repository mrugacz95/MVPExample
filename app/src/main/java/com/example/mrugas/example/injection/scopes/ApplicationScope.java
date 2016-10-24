package com.example.mrugas.example.injection.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by mruga on 24.10.2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@interface ApplicationScope {
}

