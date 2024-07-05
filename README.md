* 影像前處理
* 批量壓縮照片尺寸小工具
* OPEN CV 資料擴增小工具
  * 旋轉
  * 切割
  * 鏡像
  * 向右 / 下 翻轉
  * 水平剪力
  * 上下左右平移
  * 縮小倍率
* 批量生成 10 fold validation 資料集小工具
  * 每次執行可以分為 N (可指定N) 個fold。每一個fold規則如下：
    1. 隨機抽取生成validation Set，取後不放回
    2. 若加入validation Set之圖片，其擴增的資料不加入 training Set
    3. 可原地重新執行，會刪除後再製作新的 N - fold
* 批量生成 holdOutValidation 資料集小工具
  * 1/10分割為testing Set，1/10分割為validation Set，8/10為training Set
