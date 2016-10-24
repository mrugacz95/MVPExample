package com.example.mrugas.example.models;

/**
 * Created by mruga on 24.10.2016.
 */
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DailyMotionUsersList {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("explicit")
    @Expose
    private Boolean explicit;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("list")
    @Expose
    private List<DailyMotionUser> list = new ArrayList<>();

    /**
     *
     * @return
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     *
     * @param limit
     * The limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     *
     * @return
     * The explicit
     */
    public Boolean getExplicit() {
        return explicit;
    }

    /**
     *
     * @param explicit
     * The explicit
     */
    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The hasMore
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     *
     * @param hasMore
     * The has_more
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     *
     * @return
     * The list
     */
    public List<DailyMotionUser> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(List<DailyMotionUser> list) {
        this.list = list;
    }

}
