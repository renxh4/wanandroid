package com.example.wanandroid.bean;

import java.util.List;

public class TxBean {

    private List<DataDTO> data;
    private int errorCode;
    private String errorMsg;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataDTO {
        private String author;
        private List<ChildrenDTO> children;
        private int courseId;
        private String cover;
        private String desc;
        private int id;
        private String lisense;
        private String lisenseLink;
        private String name;
        private int order;
        private int parentChapterId;
        private boolean userControlSetTop;
        private int visible;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public List<ChildrenDTO> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLisense() {
            return lisense;
        }

        public void setLisense(String lisense) {
            this.lisense = lisense;
        }

        public String getLisenseLink() {
            return lisenseLink;
        }

        public void setLisenseLink(String lisenseLink) {
            this.lisenseLink = lisenseLink;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public boolean isUserControlSetTop() {
            return userControlSetTop;
        }

        public void setUserControlSetTop(boolean userControlSetTop) {
            this.userControlSetTop = userControlSetTop;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public static class ChildrenDTO {
            private String author;
            private List<?> children;
            private int courseId;
            private String cover;
            private String desc;
            private int id;
            private String lisense;
            private String lisenseLink;
            private String name;
            private int order;
            private int parentChapterId;
            private boolean userControlSetTop;
            private int visible;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLisense() {
                return lisense;
            }

            public void setLisense(String lisense) {
                this.lisense = lisense;
            }

            public String getLisenseLink() {
                return lisenseLink;
            }

            public void setLisenseLink(String lisenseLink) {
                this.lisenseLink = lisenseLink;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getParentChapterId() {
                return parentChapterId;
            }

            public void setParentChapterId(int parentChapterId) {
                this.parentChapterId = parentChapterId;
            }

            public boolean isUserControlSetTop() {
                return userControlSetTop;
            }

            public void setUserControlSetTop(boolean userControlSetTop) {
                this.userControlSetTop = userControlSetTop;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }
        }
    }
}
