package com.springimplant.complaintmanager.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component("lap1")
public class Laptop {
    private int lid;
    private String brand;
}
