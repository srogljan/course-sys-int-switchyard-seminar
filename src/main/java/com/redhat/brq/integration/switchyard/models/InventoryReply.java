package com.redhat.brq.integration.switchyard.models;

public class InventoryReply {

    private long articleId;

    private int reserved;

    private int left;

    public InventoryReply() {
    }

    public InventoryReply(long articleId, int reserved, int left) {
        super();
        this.articleId = articleId;
        this.reserved = reserved;
        this.left = left;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    @Override
    public String toString() {
        return "InventoryReply [articleId=" + articleId + ", reserved="
                + reserved + ", left=" + left + "]";
    }
}
