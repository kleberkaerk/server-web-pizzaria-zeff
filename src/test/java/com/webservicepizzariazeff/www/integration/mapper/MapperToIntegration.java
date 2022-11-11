package com.webservicepizzariazeff.www.integration.mapper;

public class MapperToIntegration {

    private MapperToIntegration() {
    }

    public static String fromHeaderToCsrfToken(String header){

        String[] TokenAndPath = header.split(";");

        String[] csrfToken = TokenAndPath[0].split("=");

        return csrfToken[1];
    }
}
