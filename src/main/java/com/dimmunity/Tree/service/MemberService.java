package com.dimmunity.Tree.service;

import com.dimmunity.Tree.dto.MemberDTO;
import com.dimmunity.Tree.dto.MemberRequestDTO;
import com.dimmunity.Tree.entity.MemberEntity;
import com.dimmunity.Tree.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


//전체흐름
//save에서 입력한 값이 controller로 넘어와서
// service로 넘기고 dto를 entity객체로 변환하여 repository에 save메소드로 넘김
@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // repsitory의 save 메서드 호출
        //1. dto->entity 변환
        //2. repository의 save 메소드 호출
        //(조건.jpa를 사용하기때문에 entity객체를 넘겨줘야 함)
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); //jpa가 제공하는 save메소드 사용
    }
    public boolean isEmailTaken(String email) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(email);
        return member.isPresent();
    }
    public boolean isEmailAvailable(String email) {
        return memberRepository.findByMemberEmail(email).isEmpty();
    }

    public MemberDTO login(MemberRequestDTO memberRequestDTO){
        Optional<MemberEntity> byMemeberEmail=memberRepository.findByMemberEmail(memberRequestDTO.getMemberEmail());
        if(byMemeberEmail.isPresent()){ //조회결과있음
            MemberEntity memberEntity=byMemeberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberRequestDTO.getMemberPassword())){
                //비번일치
                //entity->dto 변환 후 리턴, dto에 메소드생성
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else { //비번불일치
                return null;
                }
        } else { //조회결과없을때
            return null;
        }
    }
    public List<MemberDTO> findAll() {
        //자동완성: option+enter
        //repository와 관련된것은 무조건 entity객체로 주고 받게됨
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        //dto로 변환해서 Controller로 줘야함, entity가 여러개이므로 반복문으로 하나씩 옮겨담아야함
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;

    }


    public MemberDTO findById(Long id) {
        // 하나 조회할때 optional로 감싸줌
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); //get을통해 optional을 벗겨내서 entity -> dto 변환
        }else {
            return null;
        }
    }
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else{

            return null;
        }
    }

    //비번 변경시 인증
    public boolean validateUser(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (member.isPresent()) {
            return member.get().getMemberPassword().equals(memberDTO.getMemberPassword());
        }
        return false;
    }

    public void changePassword(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (member.isPresent()) {
            MemberEntity memberEntity = member.get();
            memberEntity.setMemberPassword(memberDTO.getMemberPassword());
            memberRepository.save(memberEntity);
        }
    }

}
