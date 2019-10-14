package com.micro.helpdesk.api;

public interface OnEventListener<T> {

        public void onSuccess(T object);
        public void onFailure(Exception e);
    }
