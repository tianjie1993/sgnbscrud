package com.sgnbs.common.excel.callback;

import com.sgnbs.common.excel.ExcelImpotResult;

import java.util.List;

public interface ExcelCallback {

    ExcelImpotResult doCallback(List list);
}
