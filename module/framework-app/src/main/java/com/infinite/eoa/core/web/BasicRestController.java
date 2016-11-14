package com.infinite.eoa.core.web;

import com.infinite.eoa.core.util.ExcelUtil;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.EmployeeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BasicRestController extends BasicController implements IRestController {
    @Autowired
    private EmployeeService employeeService;

    public Response makeResponse(ResponseCode code) {
        return new Response(code.code, code.message);
    }

    public Response makeResponse(ResponseCode code, Object data) {
        return new Response(code.code, code.message, data);
    }

    public void exportExcel(String fileName, String sheetName, List<Map<String, Object>> list,
                            String[] columnNames, String[] keys,
                            HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(sheetName, list, keys, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            IOUtils.copy(bis, bos);
        } catch (final IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
        }
    }

    protected Employee getSubjectEmployee() {
        Object username = getSubject().getPrincipal();
        if (null == username) {
            return null;
        }
        return employeeService.getEmployeeByUsername(username.toString());
    }

}
