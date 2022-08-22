package com.cryptonita.app.core.controllers.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface IExcelService {


     void downloadHistory(String start, String end, HttpServletResponse response) throws IOException;
}
