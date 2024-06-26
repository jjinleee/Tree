package com.dimmunity.Tree.dto;

import com.dimmunity.Tree.entity.MemberEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class MemberDTO { //회원 정보를 필드로 정의
    private Long id;  //pk로 등록
    private String memberEmail;
    private String memberPassword;

    //lombok 어노테이션으로 getter,setter,생성자,toString 메서드 생략 가능

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());

        return memberDTO;
    }
}
