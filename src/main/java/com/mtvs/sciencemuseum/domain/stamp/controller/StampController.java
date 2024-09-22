package com.mtvs.sciencemuseum.domain.stamp.controller;

import com.mtvs.sciencemuseum.domain.stamp.dto.StampDTO;
import com.mtvs.sciencemuseum.domain.stamp.service.StampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stamp")
@CrossOrigin(origins = "http://localhost:3000") // React 앱의 URL
public class StampController {

    @Autowired
    private StampService stampService;

    @PostMapping("/submit")
    public String submitStamp(@RequestBody StampDTO stampDTO) {
//        System.out.println("언리얼 데이터 == Received StampDTO: " + stampDTO);
//        System.out.println("언리얼 데이터 == Nickname: " + stampDTO.getNickname());
//        System.out.println("언리얼 데이터 == Enter: " + stampDTO.getEnter());
//        System.out.println("언리얼 데이터 == Activity: " + stampDTO.getActivity());
//        System.out.println("언리얼 데이터 == StartAt: " + stampDTO.getStartAt());
//        System.out.println("언리얼 데이터 == EndAt: " + stampDTO.getEndAt());
        return stampService.saveStamp(stampDTO);
    }

    @GetMapping("/all")
    public List<StampDTO> getAllStamps() {
        List<StampDTO> stampList = stampService.getAllStamps();

        // 가져온 데이터를 서버 콘솔에 출력
//        System.out.println("언리얼 데이터 == Stamp 데이터 가져오기 성공");
//        for (StampDTO stamp : stampList) {
//            System.out.println("언리얼 데이터 == Nickname: " + stamp.getNickname());
//            System.out.println("언리얼 데이터 == Enter: " + stamp.getEnter());
//            System.out.println("언리얼 데이터 == Activity: " + stamp.getActivity());
//            System.out.println("언리얼 데이터 == StartAt: " + stamp.getStartAt());
//            System.out.println("언리얼 데이터 == EndAt: " + stamp.getEndAt());
//            System.out.println("------------------------------------");
//        }

        // 로그 추가: 리턴할 데이터 확인
//        System.out.println("리턴할 데이터: " + stampList);

        return stampList;
    }


    // 연결 테스트
//    @GetMapping("/test")
//    public String test() {
//        return "Test";
//    }
}
