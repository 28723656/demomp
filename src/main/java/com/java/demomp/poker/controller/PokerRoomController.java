package com.java.demomp.poker.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.poker.entity.PokerRoom;
import com.java.demomp.poker.service.PokerRoomService;
import com.java.demomp.util.Result;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lost丶wind
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/poker/room")
public class PokerRoomController {

    @Autowired
    PokerRoomService pokerRoomService;

    /**
     * 邀请好友
     * @param selfId
     * @param otherId
     * @return
     */
    @PostMapping("/user/{selfId}/{otherId}")
    public Result invitePlayer(@PathVariable("selfId") Integer selfId,@PathVariable("otherId") Integer otherId){

        PokerRoom beInvitedMan = pokerRoomService.getOne(new QueryWrapper<PokerRoom>().eq("user_id", otherId).eq("ok",1));
        if(beInvitedMan == null){
            // 如果被邀请的玩家为空，可以邀请
            PokerRoom selfRoom = pokerRoomService.getOne(new QueryWrapper<PokerRoom>().eq("user_id", selfId));
            if(selfRoom == null){
                // 如果是初始邀请，就创建一个房间
                PokerRoom room = new PokerRoom();
                room.setUserId(selfId);
                room.setOk(1);
                PokerRoom maxRoom = pokerRoomService.getOne(new QueryWrapper<PokerRoom>().orderByDesc("room").last("limit 1"));
                if(maxRoom == null){
                    room.setRoom(1);
                }else {
                    room.setRoom(maxRoom.getRoom()+1);
                }

                PokerRoom otherRoom = new PokerRoom();
                otherRoom.setUserId(otherId);
                otherRoom.setOk(0);
                otherRoom.setRoom(room.getRoom());

                pokerRoomService.save(room);
                pokerRoomService.save(otherRoom);
            }else {
                // 不是初始邀请，就设置被邀请的那个玩家的room为1
                PokerRoom invitedMan = pokerRoomService.getOne(new QueryWrapper<PokerRoom>().eq("user_id", otherId));
                if(invitedMan == null){
                    // 如果被邀请的那个人是第一次被邀请，就插入
                    PokerRoom tempMan = new PokerRoom();
                    tempMan.setUserId(otherId);
                    tempMan.setRoom(selfRoom.getRoom());
                    tempMan.setOk(0);
                    pokerRoomService.save(tempMan);
                }else {
                    // 被邀请的那个人不是第一次被邀请,就设置ok为;
                    invitedMan.setOk(0);
                    pokerRoomService.updateById(invitedMan);
                }

            }
        }else {
            // 如果被邀请的玩家非空，不能邀请
            return new Result(false, StatusCode.OK,"不能被邀请，玩家在另一个队伍中");
        }

        return new Result(true, StatusCode.OK,"邀请成功");

    }


    /**
     * 查询自己是否被邀请了
     * @return
     */
    @GetMapping("/invited/{userId}")
    public Result searchToBeInvited(@PathVariable Integer userId){
        PokerRoom selfBeInvited = pokerRoomService.getOne(new QueryWrapper<PokerRoom>().eq("user_id", userId).eq("ok",0));
        if(selfBeInvited!=null){
            return new Result(true,StatusCode.OK,"被邀请了",selfBeInvited);
        }else {
            return new Result(false,StatusCode.OK,"没有被邀请");

        }
    }


    /**
     * 回复邀请
     * @param userId
     * @return
     */
    @PutMapping("/reply/{userId}/{ok}")
    public Result refuseInvited(@PathVariable("userId") Integer userId,@PathVariable("ok") Integer ok){
        PokerRoom pokerRoom = pokerRoomService.getOne(new QueryWrapper<PokerRoom>().eq("user_id", userId));
        pokerRoom.setOk(ok); // 设置为拒绝还是接受邀请
        pokerRoomService.updateById(pokerRoom);
        return new Result(true,StatusCode.OK,"修改成功",pokerRoom);
    }


    /**
     * 查询当前队伍玩家
     * @param room
     * @return
     */
    @GetMapping("/team/{room}")
    public Result showTeam(@PathVariable Integer room){
        List<PokerRoom> rooms = pokerRoomService.getBaseMapper().selectList(new QueryWrapper<PokerRoom>().eq("room", room));
        return new Result(true,StatusCode.OK,"查询成功",rooms);
    }


    /**
     * 查询当前队伍玩家通过自己的用户id
     * @param userId
     * @return
     */
    @GetMapping("/teamByUserId/{userId}")
    public Result showTeamByUserId(@PathVariable Integer userId){
        PokerRoom pokerRoom = pokerRoomService.getBaseMapper().selectOne(new QueryWrapper<PokerRoom>().eq("user_id", userId).eq("ok", 1));
        if(pokerRoom == null){
            // 如果自己没有加入什么队伍的话
            return new Result(false,StatusCode.OK,"查询成功");
        }else {
            List<PokerRoom> rooms = pokerRoomService.getBaseMapper().selectList(new QueryWrapper<PokerRoom>().eq("room", pokerRoom.getRoom()).eq("ok", 1));
            return new Result(true,StatusCode.OK,"查询成功",rooms);
        }
    }


}
