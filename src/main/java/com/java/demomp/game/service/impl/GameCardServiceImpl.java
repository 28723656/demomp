package com.java.demomp.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.demomp.game.VO.CardDictionaryVO;
import com.java.demomp.game.VO.GameMyCardVO;
import com.java.demomp.game.VO.TempForCardDictionaryVO;
import com.java.demomp.game.entity.*;
import com.java.demomp.game.mapper.GameCardMapper;
import com.java.demomp.game.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java.demomp.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lost丶wind
 * @since 2019-08-26
 */
@Service
public class GameCardServiceImpl extends ServiceImpl<GameCardMapper, GameCard> implements GameCardService {

    @Autowired
    GameConfigService gameConfigService;

    @Autowired
    GameCostService gameCostService;

    @Autowired
    GameUpgradeService gameUpgradeService;

    @Autowired
    GameLuckyConfigService gameLuckyConfigService;

    @Autowired
    GamePercentService gamePercentService;

    @Autowired
    GameMyCardService gameMyCardService;

    @Autowired
    GameCardService gameCardService;

    @Autowired
    RedisTemplate redisTemplate;

    // redis存储的最大时间
    static final Integer REDIS_MAX_TIME = 7;


    /**
     * 卡片删除
     * @param cardId
     * @return
     */
    public int deleteCardByCardId(Integer cardId) {

        // 如果有人在抽奖概率中引用了这张卡，就不能被删除
        List<GameLuckyConfig> cardForLuckyList = gameLuckyConfigService.getBaseMapper().selectList(new QueryWrapper<GameLuckyConfig>().eq("card_id", cardId));
        if(cardForLuckyList.size() == 0){
            // 1.删除 t_game_card
            int deleteNums = baseMapper.deleteById(cardId);
            // 2.删除t_game_config
            gameConfigService.deleteCardByCardId(cardId);
            // 3.删除t_game_cost
            gameCostService.deleteCardByCardId(cardId);
            // 4.删除t_game_myCard(未完成)
            // 5.删除t_game_upgrade
            gameUpgradeService.deleteCardByCardId(cardId);
            if(deleteNums > 0){
                return StatusCode.OK;
            }else {
                return StatusCode.ERROR;
            }
        }else {
            return StatusCode.CANNOTDELETE;
        }
    }

    /**
     * 保存卡片
     * @param gameCard
     */
    public void saveCard(GameCard gameCard) {
        baseMapper.insert(gameCard);
        // 2.像t_game_percent表中插入默认数据
        List<GamePercent> list = new ArrayList<>();
        list.add(generateEntity(1,70,gameCard.getId()));
        list.add(generateEntity(2,20,gameCard.getId()));
        list.add(generateEntity(5,9,gameCard.getId()));
        list.add(generateEntity(20,1,gameCard.getId()));
        gamePercentService.saveBatch(list);
    }

    // 展示卡片，极速版
    public List<GameMyCardVO> showMyCard(Integer userId) {
        return baseMapper.showMyCard(userId);
    }

    /**
     * 卡片字典
     * @return
     */
    public List<CardDictionaryVO> showCardDictionary() {
       // 1.先查出所有的卡片
        List<GameCard> gameCardList = baseMapper.selectList(null);

        // 2.查出每个星级 消耗的卡片数量，可以升级的等级
        List<TempForCardDictionaryVO> cardAndStarNumList = baseMapper.selectEveryStarDetail();

        // 3.查找每一个卡片满级的属性
        List<GameCost> maxList = gameCostService.getMaxList();

        // 4.查找每一个卡片能在哪个卡包里面产出
        List<TempForCardDictionaryVO> outputPlaceList = gameLuckyConfigService.getEveryCardOutputPlace();

        // 最后.分类查找
        // 用于存储最后的结果
        List<CardDictionaryVO> finalList = new ArrayList<>();
        for(int i=0;i<gameCardList.size();i++){
            CardDictionaryVO cardDictionaryVO = new CardDictionaryVO();
            GameCard gameCard = gameCardList.get(i);
            // 处理1：设置卡片的一些属性
            cardDictionaryVO.setId(gameCard.getId());
            cardDictionaryVO.setName(gameCard.getName());
            cardDictionaryVO.setType(gameCard.getType());
            cardDictionaryVO.setSkill(gameCard.getSkill());
            cardDictionaryVO.setTopStar(gameCard.getTopStar());


            // 处理2：每个星级 消耗的卡片数量，可以升级的等级
            List<Integer> starCostNum = new ArrayList<>();
            List<Integer> starRankNum = new ArrayList<>();
            List<Integer> star = new ArrayList<>();
            for(int a = 0;a<cardAndStarNumList.size();a++){
                TempForCardDictionaryVO tempForCardDictionaryVO = cardAndStarNumList.get(a);
                if(tempForCardDictionaryVO !=null && tempForCardDictionaryVO.getCardId() ==gameCard.getId() ){
                    star.add(tempForCardDictionaryVO.getStar());
                    starCostNum.add(tempForCardDictionaryVO.getStarNum());
                    starRankNum.add(tempForCardDictionaryVO.getRankNum());
                }
            }
            cardDictionaryVO.setStar(star);
            cardDictionaryVO.setStarCostNum(starCostNum);
            cardDictionaryVO.setStarRankNum(starRankNum);

            // 处理3：满级效果
            for(int b = 0;b<maxList.size();b++){
                GameCost gameCost = maxList.get(b);
                if(gameCost!= null && gameCost.getCardId() == gameCard.getId() ){
                    if(gameCard.getSkill() == 1){
                        cardDictionaryVO.setIncCoinFull(gameCost.getIncCoin());
                    }else if(gameCard.getSkill() == 2){
                        cardDictionaryVO.setIncExperienceFull(gameCost.getIncExperience());
                    }else if(gameCard.getSkill() == 3){
                        cardDictionaryVO.setLowPercentFull(gameCost.getLowPercent());
                        cardDictionaryVO.setTopPercentFull(gameCost.getTopPercent());
                    }
                }
            }


            // 处理4：产出地方
            List<String > luckyName = new ArrayList<>();
            for(int c=0;c<outputPlaceList.size();c++){
                TempForCardDictionaryVO tempForCardDictionaryVO = outputPlaceList.get(c);
                if(tempForCardDictionaryVO!=null && tempForCardDictionaryVO.getCardId()!=null && tempForCardDictionaryVO.getCardId() == gameCard.getId()){
                    luckyName.add(tempForCardDictionaryVO.getLuckyName());
                }
            }
            cardDictionaryVO.setRewardPlace(luckyName);


            // 最后：统一添加
            finalList.add(cardDictionaryVO);
        }
       return finalList;

    }


    /**
     * 通过用户id，查询一系列的卡片相关属性,这个方法巨慢,目前已经废了，用上面的方法（等我的优化版）
     * @param userId
     * @return
     */
    public List<Map<String, Object>> showMyCardBefore(Integer userId) {

        List<Map<String, Object>> redisList = (List<Map<String, Object>>) redisTemplate.opsForValue().get("showMyCard_"+userId);
        // 如果redis里面没有数据
        if(redisList == null){
            // 先查询用户的拥有卡片集合
            List<GameMyCard> myCardList = gameMyCardService.list(new QueryWrapper<GameMyCard>().eq("user_id", userId));
            // 如果能够查询到卡片
            List<Map<String, Object>> list = new ArrayList<>();
            if(myCardList!=null){
                // 对每一个卡片进行详细查询，把结果放入map中
                for(int i=0;i<myCardList.size();i++){
                    GameMyCard myCardEntity =  myCardList.get(i);
                    /**
                     * 卡片数量:24/20 升星
                     * 当前星级:2 /3
                     * 当前等级:14 升级
                     * 升级花费:140G
                     * 当前效果：金币+7%
                     * 下一级：金币+7.1%
                     */

                    Map<String, Object> map = new HashMap<>();


                    // 当前卡片数量
                    map.put("cardNum",myCardEntity.getNum());
                    // 当前星级
                    map.put("currentStar",myCardEntity.getCurrentStar());
                    // 当前等级
                    map.put("currentRank",myCardEntity.getCurrentRank());
                    // 下一级升星需要多少卡片
                    GameUpgrade gameUpgrade = gameUpgradeService.getBaseMapper().selectOne(new QueryWrapper<GameUpgrade>().eq("card_id", myCardEntity.getCardId()).eq("star", myCardEntity.getCurrentStar() + 1));
                    map.put("updateStarNeedNum",gameUpgrade.getNum());
                    // 升级下一级的时候需要花费多少金币
                    GameCost gameCostNext = gameCostService.getBaseMapper().selectOne(new QueryWrapper<GameCost>().eq("card_id", myCardEntity.getCardId()).eq("rank", myCardEntity.getCurrentRank() + 1));
                    GameCost gameCostNow = gameCostService.getBaseMapper().selectOne(new QueryWrapper<GameCost>().eq("card_id", myCardEntity.getCardId()).eq("rank", myCardEntity.getCurrentRank()));
                    map.put("cost",gameCostNext.getCost());

                    GameCard gameCard = gameCardService.getBaseMapper().selectById(myCardEntity.getCardId());
                    map.put("skill",gameCard.getSkill());
                    // 什么卡片
                    map.put("cardType",gameCard.getType());
                    // 最高星级
                    map.put("topStar",gameCard.getTopStar());
                    // 卡片的名字
                    map.put("cardName",gameCard.getName());
                    int skill = gameCard.getSkill();
                    // 根据技能，选择当前效果和下一级的效果
                    if(skill == 1){
                        map.put("incSkill",gameCostNow.getIncCoin());
                        map.put("incSkillNext",gameCostNext.getIncCoin());
                    }else if(skill == 2){
                        map.put("incSkill",gameCostNow.getIncExperience());
                        map.put("incSkillNext",gameCostNext.getIncExperience());
                    }else if(skill == 3){
                        map.put("incSkillLow",gameCostNow.getLowPercent());
                        map.put("incSkillTop",gameCostNow.getTopPercent());
                        map.put("incSkillLowNext",gameCostNext.getLowPercent());
                        map.put("incSkillTopNext",gameCostNext.getTopPercent());
                    }
                    list.add(map);
                }
                redisTemplate.opsForValue().set("showMyCard_"+userId,list,2, TimeUnit.MINUTES);
                return list;
            }else {
                return null;
            }

        }else {
            return redisList;
        }

    }




    public GamePercent generateEntity(int nums,long percent,int cardId ){
        GamePercent gamePercent = new GamePercent();
        gamePercent.setNums(nums);
        gamePercent.setPercent(BigDecimal.valueOf(percent));
        gamePercent.setCardId(cardId);
        return gamePercent;
    }
}
