package cn.edu.nuaa.myclinic.pojo.workflow;

import cn.edu.nuaa.myclinic.pojo.baseLib.BaseDTO;

public class UserEntityDTO extends BaseDTO {
    protected String firstName;
    protected String lastName;

    public UserEntityDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
