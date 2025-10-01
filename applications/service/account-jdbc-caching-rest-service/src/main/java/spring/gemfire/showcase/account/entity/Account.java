package spring.gemfire.showcase.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Region(name = "Account")
public class Account
{
    private String id;
    private String name;
}




