package com.six.ems.web.service.interfaces.classes;

import com.six.ems.entity.tables.Classes;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface ClassService {
    List<Classes> getClassByStuName(String stuName);

    List<Classes> getClassByStuId(Integer stuId);

    List<Classes> getClassByTeaName(String teaName);

    List<Classes> getClassByTeaId(Integer teaId);

    String getClassByStuName(String stuName, String className, Integer page, Integer pageSize);
    String getClassByTeaName(String teaName, String className, Integer page, Integer pageSize);
}
