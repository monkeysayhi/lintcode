package com.msh.solutions._128_Hash_Function;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
class Solution {
  public static final int MAGIC_NUM = 33;
  /**
   * @param key: A String you should hash
   * @param HASH_SIZE: An integer
   * @return an integer
   */
  public int hashCode(char[] key,int HASH_SIZE) {
    // write your code here
    if (key == null || key.length == 0) {
      return 0;
    }

    long hash = key[0] % HASH_SIZE;
    int magicNum = MAGIC_NUM % HASH_SIZE;
    for (int i = 1; i < key.length; i++) {
      hash = ((hash * magicNum) % HASH_SIZE + key[i] % HASH_SIZE)
          % HASH_SIZE;
    }
    return (int) hash;
  }
}