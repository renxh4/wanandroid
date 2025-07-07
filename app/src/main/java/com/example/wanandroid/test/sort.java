package com.example.wanandroid.test;

import java.util.HashMap;

public class sort {
    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
     *
     * 你可以按任意顺序返回答案。
     *
     * 个人思路很暴力，就是通过俩次遍历，实现所有组合的加法，来判断是否有符合条件的元素.时间复杂度O(n^2)
     */
    public int[] twoSum(int[] nums, int target) {
        int[] arr = new int[2];
        for (int i = 0; i < nums.length-1; i++) {
            for (int j = i+1; j < nums.length; j++) {
                int temp = nums[i]+nums[j];
                if (temp == target){
                    arr[0] = i;
                    arr[1] = j;
                    return arr;
                }
            }
        }

        return arr;
    }


    /**
     * 进阶版思路 利用hash 进行优化 时间复杂度O(n)
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum1(int[] nums, int target) {
        int[] arr = new int[2];
        HashMap<Integer, Integer> integerIntegerHashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (integerIntegerHashMap.containsKey(target-nums[i])){
                return new int[]{integerIntegerHashMap.get(target-nums[i]),i};
            }
            integerIntegerHashMap.put(nums[i],i);
        }

        return arr;
    }

    /**
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 示例 1:
     *
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     *
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     */
//    public List<List<String>> groupAnagrams(String[] strs) {
//
//    }


}
