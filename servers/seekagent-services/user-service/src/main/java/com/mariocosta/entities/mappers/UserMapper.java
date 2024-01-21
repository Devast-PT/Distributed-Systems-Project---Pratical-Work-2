package com.mariocosta.entities.mappers;


import com.mariocosta.clients.user.dtos.UserDto;
import com.mariocosta.entities.Userr;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    UserDto UserToUserDto (Userr userr);

    Userr UserDtoToUser (UserDto user);

}
