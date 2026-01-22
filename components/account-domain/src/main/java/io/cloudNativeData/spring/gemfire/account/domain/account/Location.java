package io.cloudNativeData.spring.gemfire.account.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location
{
    private String id;
    private String address;
    private String city;
    private String stateCode;
    private String zipCode;
}
