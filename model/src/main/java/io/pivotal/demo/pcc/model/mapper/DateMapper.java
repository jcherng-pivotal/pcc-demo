package io.pivotal.demo.pcc.model.mapper;

import java.util.Date;

public class DateMapper {

    public Long asLong(Date date) {
        return date != null ? date.getTime() : null;
    }

    public Date asDate(Long time) {
        return time != null ? new Date(time) : null;
    }
}