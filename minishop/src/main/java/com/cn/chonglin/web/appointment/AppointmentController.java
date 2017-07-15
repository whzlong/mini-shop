package com.cn.chonglin.web.appointment;

import com.cn.chonglin.bussiness.appointment.service.AppointmentService;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
import com.cn.chonglin.bussiness.appointment.vo.SimpleAppointmentVo;
import com.cn.chonglin.common.PaginationResult;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.appointment.form.AppointmentForm;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 预约控制器
 */
@Controller
public class AppointmentController{
    @Autowired
    private AppointmentService appointmentService;

    /**
     * 提交预约信息
     *
     * @param appointmentForm
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "client/book", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseResult<Object> book(@Valid AppointmentForm appointmentForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        appointmentService.add(appointmentForm.toDomain());

        return ResponseResult.success(null);
    }

    /**
     * 客户端获取预约信息
     *
     * @param period
     * @return
     */
    @GetMapping(value = "client/appointments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseResult<List<SimpleAppointmentVo>> getAppointments(@RequestParam(required = false, defaultValue = "0") String period){
        return ResponseResult.success(appointmentService.queryAppointments(period));
    }

    @PostMapping(value = "client/appointments/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseResult<Object> updateAppointments(@RequestParam(required = true, defaultValue = "") String id
                                                                , @RequestParam(required = false) String bookDate
                                                                , @RequestParam(required = false) String bookTime){
        appointmentService.updateAppointmentDatetime(id, bookDate, bookTime);

        return ResponseResult.success(null);
    }

    @PostMapping(value = "client/appointments/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> deleteClientAppointment(@RequestParam String id){

        appointmentService.delete(id);

        return ResponseResult.success(null);
    }

    @GetMapping(value = "admin/appointment-list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    PaginationResult<AppointmentVo> query(@RequestParam(required = false) String bookDateFrom,
                                          @RequestParam(required = false) String bookDateTo,
                                          @RequestParam(required = false, defaultValue = "Pending") String state,
                                          @RequestParam(required = false, defaultValue = "0") @Min(0) int currentPage,
                                          @RequestParam(required = false, defaultValue = "15") @Min(0) @Max(200) int size){

        currentPage -= 1;

        return PaginationResult.success(appointmentService.query(bookDateFrom, bookDateTo, state, size, currentPage));
    }

    @PostMapping(value = "admin/appointments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> edit(@Valid AppointmentForm appointmentForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        appointmentService.edit(appointmentForm.toDomain());

        return ResponseResult.success(null);
    }

    @PostMapping(value = "admin/appointments/confirm", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseResult<Object> confirmWithCustomer(@Valid AppointmentForm appointmentForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        appointmentService.confirmWithCustomer(appointmentForm.toDomain());

        return ResponseResult.success(null);
    }

    @PostMapping(value = "admin/appointments/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
            ResponseResult<Object> delete(@RequestParam String id){

        appointmentService.delete(id);

        return ResponseResult.success(null);
    }

    @GetMapping(value = "admin/appointments/download")
    public void download(HttpServletResponse res) throws IOException{
        res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        res.setHeader("Content-Disposition", "attachment; filename=test.zip");

        ZipOutputStream zipOutputStream = new ZipOutputStream(res.getOutputStream());

        List<String> filePathLists = Lists.newArrayList();
        filePathLists.add("http://www.phosion.cn/uploadfiles/image/201703/545.jpg");
        filePathLists.add("http://www.phosion.cn/uploadfiles/image/201703/544.jpg");

        int i = 0;

        for(String filePath : filePathLists){
            URL url = new URL(filePath);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();

            zipOutputStream.putNextEntry(new ZipEntry(String.valueOf(i++) + ".jpg"));
            byte[] imgBytes = readInputStream(inputStream);
            zipOutputStream.write(imgBytes);
            inputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.flush();
        zipOutputStream.close();

    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len = 0;
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }

        inputStream.close();

        return outputStream.toByteArray();
    }

}
