package com.miao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 被用户喜欢的次数
 *
 * @author miao
 */
@Data
@Table(name = "prised_movie")
public class PrisedMovie implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "movie_id")
    private String movieId;

}