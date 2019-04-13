package com.bigdata.olearn.repository;

import com.bigdata.olearn.neo.PointNode;
import com.bigdata.olearn.neo.UserPointNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointNodeRepository extends Neo4jRepository<UserPointNode,Long> {
    //todo 将函数改为多用户模式uId
    List<UserPointNode> findUserPointNodesByPAreaIsAndUIdIs(String pArea,Long uId);//根据pArea and uId找到对应的point

    List<UserPointNode> findUserPointNodesByPAreaIdIsAndUIdIs(Long pAreaId,Long uId);//根据pAreaId and uId找到对应的point

    UserPointNode findUserPointNodeByPNameIdAndUIdIs(Long pNameId,Long uId);//通过知识点得到
    /*
    * 得到用户图谱中某个知识点的所有前导节点
    * */
    /**
     * 根据point的pnameId得到其所有的前导课程
     * @param pNameId -- 知识点的名称Id
     * @return
     */
    @Query("match (a:userpoint{pNameId:{0}})-[r*]->(b:userpoint) return b")
    List<UserPointNode> getUserPreviousPointByPNameId(Long pNameId);

    /*
    * 修改用户知识图谱中知识点的状态 0学习完成/1正在学习/2推荐学习/3未学习
    * */
    //把一个知识点设为正在学习状态，其下一个知识点设为推荐学习状态
    @Query("match(a:userpoint)-[r:USERPREVIOUS]->(b:userpoint{pNameId:{0}})set b.upStatus=1, a.upStatus=2")
    void setUserPointLearning(Long pNameId);

    //把一个单独知识点（即没有后续课程的知识点）设为正在学习状态
    @Query("match(a:userpoint{pNameId:{0}})set a.upStatus=1")
    void setSoloUserPointLearning(Long pNameId);

    //把某个知识点的状态改为0学习完成
    @Query("match(a:userpoint{pNameId:{0}})set a.upStatus=0")
    void setUserPointFinished(Long pNameId);

}
