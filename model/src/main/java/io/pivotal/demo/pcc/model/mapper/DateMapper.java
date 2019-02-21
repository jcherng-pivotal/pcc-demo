package io.pivotal.demo.pcc.model.mapper;

import java.util.Date;

public class DateMapper {

    public Long map(Date date) {
        return date != null ? date.getTime() : null;
    }

    public Date map(Long time) {
        return time != null ? new Date(time) : null;
    }
}