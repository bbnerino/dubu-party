package com.dubu.party.domain.user.service;

import com.dubu.party.domain.user.db.entity.User;
import com.dubu.party.domain.user.db.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 롤백
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public User makeUser(String id) {
        User user = new User();
        user.setUserLoginId("user"+id);
        user.setUserPassword("1234");
        user.setUserNickname("user"+id+id);
        user.setUserEmail("beadf"+id+"@naver.com");
        user.setUserPhone("010-"+id+"-1234");
        return user;
    }


    @Test
    void 회원가입() throws Exception {
        User user = makeUser("5");
        Long saveId = userService.saveUser(user);
        System.out.println("saveId = " + saveId);
        User findUser = userRepository.getById(saveId);
        assertThat(user.getUserEmail()).isEqualTo(findUser.getUserEmail());
    }

    @Test
    void 모든유저조회() {
        User user1 = makeUser("1");
        User user2 = makeUser("2");
        User user3 = makeUser("3");
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        assertThat(userService.getAllUsers().size()).isEqualTo(3);
    }

    @Test
    void 유저조회() {
        User user1 = makeUser("1");
        userService.saveUser(user1);
        assertThat(userService.getUserById(user1.getUserId())).isEqualTo(user1);
    }

    @Test
    void 회원삭제() {
        User user1 = makeUser("1");
        User user2 = makeUser("2");
        User user3 = makeUser("3");
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.deleteUser(user1.getUserId());
        assertThat(userService.getAllUsers().size()).isEqualTo(2);
    }

    @Test
    void 회원수정() {
        User user1 = makeUser("1");
        userService.saveUser(user1);
        user1.setUserEmail("할로할로");
        assertThat(userService.getUserById(user1.getUserId()).getUserEmail()).isEqualTo("할로할로");
    }

    @Test
    void 비밀번호변경() {
        User user1 = makeUser("6");
        userService.saveUser(user1);
        userService.updatePassword(user1.getUserId(),"1324");
        assertThat(userService.getUserById(user1.getUserId()).getUserPassword()).isEqualTo("1324");
    }
    @Test
    void 중복유저(){
        User user1 = makeUser("10");
        userService.saveUser(user1);
        User user2 = makeUser("10");
        assertThrows(IllegalStateException.class, () -> {
            userService.saveUser(user2);
        });
    }
}