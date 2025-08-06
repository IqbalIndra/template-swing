package com.learn.shirologin.model;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

@Data
@Builder(builderMethodName = "of")
public class AlternativeDataSource implements Serializable {
    private static final long serialVersionUID = -497447968323218839L;

    private Long id;
    private String code;
    private String schoolYear;
    private String major;
    private String classRoom;
    private File fileSource;
    private String filename;
    private StatusAlternative status;
    private boolean deleted;

}
