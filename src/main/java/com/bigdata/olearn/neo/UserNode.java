package com.bigdata.olearn.neo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "user")
public class UserNode implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private  String name;

    private String userId;

    private String companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
//    @Relationship(type = "CALLED", direction = Relationship.UNDIRECTED)
//    public Set<ServiceNode> calledServices;
//
//
//    public void calledWith(ServiceNode serviceNode) {
//        if (calledServices == null) {
//            calledServices = new HashSet<>();
//        }
//        calledServices.add(serviceNode);
//    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

//    @Override
//    public String toString() {
//        return "UserNode{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", userId='" + userId + '\'' +
//                ", companyId='" + companyId + '\'' +
//                ", calledServices=" + calledServices +
//                '}';
//    }

}
