package api.resource;

import java.util.ArrayList;

public class Root {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private ArrayList<Data> data;
    private Support support;

    public Root() {
    }

    public Root(Integer page, Integer per_page, Integer total, Integer total_pages, ArrayList<Data> data, Support support) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
        this.support = support;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public Support getSupport() {
        return support;
    }
}
