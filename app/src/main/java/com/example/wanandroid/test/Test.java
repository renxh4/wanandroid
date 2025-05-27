package com.example.wanandroid.test;

import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.Queue;

public class Test {

    public static void aa() {
        System.out.println("sdsada");
    }

    //冒泡排序

    public void bb(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }


    }


    //插入排序
    public static void cc(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = temp;
        }
    }

    //归并排序

    public void dd(int[] arr) {
        int[] temp = arr.clone();

        sort(arr, 0, arr.length - 1, temp);
    }

    public void sort(int[] arr, int left, int right, int[] temp) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        sort(arr, left, mid, temp);
        sort(arr, mid + 1, right, temp);
        megra(arr, left, mid, right, temp);
    }

    public void megra(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left; //左区间起始指针
        int j = mid + 1;//右区间起始指针
        int k = 0; //临时指针

        //合并俩个数组
        while (i <= mid && j <= right) {
            temp[k++] = arr[i] > arr[j] ? arr[j++] : arr[i++];
        }

        //处理剩余元素
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        //将数组拷贝到原来数组
        System.arraycopy(temp, 0, arr, left, k);
    }


    //快速排序

    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int point = partition(arr, left, right);
            // 基准元素一定比左边的数大，所以左边分区最大值是：pivot - 1，分区范围是[left, pivot - 1]
            quickSort(arr, left, point - 1);
            // 基准元素一定比右边的数小，所以右边分区最小值是：pivot + 1，分区范围是[pivot + 1, right]
            quickSort(arr, point + 1, right);
        }
    }

    private int partition(int[] arr, int left, int right) {
        //定义基准元素，为最左边元素
        int point = arr[left];
        //从右边开始遍历，寻找比基准元素小的元素
        while (left < right) {
            while (left < right && arr[right] >= point) {
                //未找到
                right--;
            }
            //找到了就放在最左边
            // 第一次进入时,基准元素已存放到临时值pivotValue了，第一次就相当于放到基准位置了，同时，arr[right]也腾出了一个位置
            arr[left] = arr[right];
            //然后从最左边开始遍历，找到比基准值大的元素

            while (left < right && arr[left] <= point) {
                //未找到
                left++;
            }

            //找到了就放到右边位置
            arr[right] = arr[left];

        }
        //遍历结束就说明left == right,然后把基准值放在腾出的位置‘
        arr[left] = point;
        //返回基准值
        return left;
    }

    //二分查找
    public int ee(int[] arr, int value) {
        int low = 0;
        int hight = arr.length - 1;

        while (low <= hight) {
            int mid = (low + hight) / 2;

            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else if (arr[mid] > mid) {
                hight = mid - 1;
            }
        }

        return -1;
    }


    //树的前序遍历,先打印自身，然后左边，然后右边
    public void sort(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        sort(node.left);
        sort(node.right);

    }

    //树的中序遍历 ，先打印左边，在打印自己，在打印右边
    public void sort2(Node node) {
        if (node == null) {
            return;
        }
        sort2(node.left);
        System.out.println(node.value);
        sort2(node.right);
    }
    //输的后序遍历,先打印左边，在打印右边，在打印自己

    public void sort3(Node node) {
        if (node == null) {
            return;
        }
        sort2(node.left);
        sort2(node.right);
        System.out.println(node.value);
    }


//广度优先搜索 就是地毯式层层推进搜索，即先搜索距离定点最近的，然后是次距离近的，依次向外搜索

//树的广度优先搜索

    public static void bfsTree(Node root) {
        if (root == null) return;
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();//获取当层的节点数量

            for (int i = 0; i < size; i++) {
                Node node = queue.poll(); //取出当前节点
                System.out.print(node.value);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            System.out.println();
        }
    }

    //图的广度优先搜索

    public static void bfsGraph(GraphBFS graphBFS,int start, int v) {
        boolean[] visitied = new boolean[v];
        Queue<Integer> queue = new LinkedList<>();

        visitied[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()){
            Integer poll = queue.poll();
            System.out.print(poll);

            LinkedList<Integer> integers = graphBFS.adj[poll];

            for (Integer aa : integers) {
                if (!visitied[aa]){
                    visitied[aa] = true;
                    queue.offer(aa);
                }
            }

        }
    }

    //广度优先搜索 最短路径
    public static void bfsGraph(GraphBFS graphBFS,int start, int end,int v) {
        boolean[] visitied = new boolean[v];
        Queue<Integer> queue = new LinkedList<>();
        visitied[start] = true;
        queue.offer(start);

        int[] pre = new int[v];

        for (int i = 0; i < v; i++) {
            pre[i] = -1;
        }

        while (!queue.isEmpty()){
            Integer poll = queue.poll();
            System.out.print(poll);

            LinkedList<Integer> integers = graphBFS.adj[poll];

            for (Integer aa : integers) {
                if (!visitied[aa]){

                    pre[aa] = poll;
                    if (aa == end){
                        print(pre,start,end);
                        return;
                    }

                    visitied[aa] = true;
                    queue.offer(aa);
                }
            }

        }
    }


    public static void print(int[] pre,int start , int end){
        if (pre[end] != -1 && start!=end){
            print(pre,start,pre[end]);
        }
        System.out.print(end);
    }


    //深度优先搜索，像是走迷宫，随机选择一个路口一直走到终点，如果不是出口，回退到重新选择路口
    static boolean found = false; //是否发现出口
    public static void dfs(GraphBFS graphBFS,int start,int end ,int v){
        found = false;
        boolean[] visited = new  boolean[v];
        int[] pre = new int[v];

        for (int i = 0; i < v; i++) {
            pre[i] = -1;
        }

        recurDfs(graphBFS,pre,visited,start,end);

        print(pre,start,end);
    }

    private static void recurDfs(GraphBFS graphBFS, int[] pre, boolean[] visited, int start, int end) {
        if (found ==true) return;
        visited[start] = true;
        if (start == end){
            found = true;
            return;
        }

        LinkedList<Integer> integers = graphBFS.adj[start];
        for (Integer aa: integers){
            if (!visited[aa]){
               pre[aa] = start ;
               recurDfs(graphBFS,pre,visited,aa,end);
            }
        }
    }


    /**
     * 贪心算法，当我们看一类问题时，我们要考虑到贪心算法
     * 针对一组数据，限制值和期望值，在满足限制值的情况下，选出几组数据，期望值最大
     *
     * 比如硬币找零问题
     *
     * [硬币问题示例]
     * 目标：用最少硬币支付63元
     * 可用硬币：1, 5, 10, 20, 50元
     *
     * 贪心策略：
     * 63 → 50 + 10 + 1*3 = 5枚
     * 实际最优解：20*3 + 1*3 = 6枚（当50元不存在时）
     */


    /**
     * 分治算法
     * 个定义看起来有点类似递归的定义。关于分治和递归的区别，，分治算法是一种处理问题的思想，递归是一种编程技巧。
     * 实际上，分治算法一般都比较适合用递归来实现。
     * 分治算法的递归实现中，每一层递归都会涉及这样三个操作：
     * 分解：将原问题分解成一系列子问题；
     * 解决：递归地求解各个子问题，若子问题足够小，则直接求解；
     * 合并：将子问题的结果合并成原问题。
     *
     * 经典了例子   归并排序
     *
     *
     * 分治算法能解决的问题，一般需要满足下面这几个条件：
     * 原问题与分解成的小问题具有相同的模式；
     * 原问题分解成的子问题可以独立求解，
     * 子问题之间没有相关性，这一点是分治算法跟动态规划的明显区别，等我们讲到动态规划的时候，会详细对比这两种算法；
     * 具有分解终止条件，也就是说，当问题足够小时，可以直接求解；
     * 可以将子问题合并成原问题，而这个合并操作的复杂度不能太高，否则就起不到减小算法总体复杂度的效果了。
     */


    /**
     * 回溯算法  深度优先搜索就是回溯算法
     *
     *我们把问题求解的过程分为多个阶段。
     * 每个阶段，我们都会面对一个岔路口，
     * 我们先随意选一条路走，
     * 当发现这条路走不通的时候（不符合期望的解）
     * ，就回退到上一个岔路口，另选一种走法继续走。
     *
     */

    /**
     * 八皇后回溯算法案例案例
     * 我们有一个 8x8 的棋盘，希望往里放 8 个棋子（皇后）
     * ，每个棋子所在的行、列、对角线都不能有另一个棋子。
     * 你可以看我画的图，第一幅图是满足条件的一种方法，
     *
     *
     * 我们把这个问题划分成 8 个阶段，
     * 依次将 8 个棋子放到第一行、第二行、第三行……第八行。
     * 在放置的过程中，我们不停地检查当前放法，是否满足要求。
     * 如果满足，则跳到下一行继续放置棋子；如果不满足，那就再换一种放法，继续尝试。
     *
     */

    public int[] result = new int[8]; //下标表示行，值表示列

    public void cal8Queue(int row){
        if (row == 8){
            //表示已经排列完成
            return;
        }

        for (int colum = 0; colum < 8; colum++) { //每一行中有8中放发
            if (isok(row,colum)){//判断放发是否满足
                result[row] = colum;//满足记录
                cal8Queue(row+1);//继续下一行
            }
        }



    }

    private boolean isok(int row, int colum) {
        //主要功能是判断是否满足,要判断，上 下 对角线
        int leftup = colum -1; //上面一列
        int rightup  = colum +1;// 下面一列

        for (int i = row -1; i >=0; i--) {  //逐行向上检查
            if (result[i] == colum) return false;//表示上面有数据
            if (leftup >=0) {//左对角线处
                if (result[i] == leftup) return false;
            }

            if (rightup <=8){ //右侧对角线处是否有值
                if (result[i] == rightup) return false;
            }

            --leftup;
            ++rightup;
        }
        return true;
    }

    /**
     * 动态规划
     *
     * 背包问题
     *
     * 对于一组不同重量、不可分割的物品，我们需要选择一些装入背包，在满足背包最大重量限制的前提下，背包中物品总重量的最大值是多少呢？
     */

    //回溯方式解决，就是穷举出所有的情况，来找出满足条件的结果

    // 回溯算法实现。注意：我把输入的变量都定义成了成员变量。
    private int maxW = Integer.MIN_VALUE;
    // 结果放到maxW中
     private int[] weight = {2,2,4,6,3};
     // 物品重量
      private int n = 5;
     // 物品个数
      private int w = 9; // 背包承受的最大重量
    public void aaa(int i,int cw){

        if (cw == w || i == n) {
            if (cw > maxW) maxW = cw;
            return;
        }

        aaa(i+1,cw);//不装i个物品

        if (cw + weight[i] < w){//装i个物品
            aaa(i+1,cw+weight[i]);
        }
    }





}












