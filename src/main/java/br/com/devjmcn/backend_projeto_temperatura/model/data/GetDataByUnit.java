package br.com.devjmcn.backend_projeto_temperatura.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetDataByUnit {
    long dateTime;
    double refMin;
    double refCur;
    double refMax;
    double envMin;
    double envCur;
    double envMax;
    String userName;
}

