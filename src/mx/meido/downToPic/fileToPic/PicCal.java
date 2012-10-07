package mx.meido.downToPic.fileToPic;

public class PicCal {
	public static class PicData{
		public PicData(){};
		
		//需要补充的字节
		private long need;
		//宽-像素数
		private long pixel_y;
		//高-像素数
		private long pixel_x;
		//补充完后总字节数
		private long totalSize;
		//源文件字节数
		private long srcSize;
		
		public long getNeed() {
			return need;
		}
		public void setNeed(long need) {
			this.need = need;
		}
		public long getPixel_y() {
			return pixel_y;
		}
		public void setPixel_y(long pixelY) {
			pixel_y = pixelY;
		}
		public long getPixel_x() {
			return pixel_x;
		}
		public void setPixel_x(long pixelX) {
			pixel_x = pixelX;
		}
		public long getTotalSize() {
			return totalSize;
		}
		public void setTotalSize(long totalSize) {
			this.totalSize = totalSize;
		}
		public long getSrcSize() {
			return srcSize;
		}
		public void setSrcSize(long srcSize) {
			this.srcSize = srcSize;
		}
		
	}
	
	public static PicData calPic(long picSize){
		PicData picData = new PicData();
		
		//��Ҫ������ֽ�
		long need = 0;
		//��-������
		long pixel_y = 0;
		//��-������
		long pixel_x = 0;
		//����������ֽ���
		long totalSize = 0;
		//Դ�ļ��ֽ���
		long srcSize = picSize;
		
		//need�ֽڼ���-part1
		need = picSize%8;
		if(need != 0){
			need = 8 - need;
		}
		
		//��ǰ������ͳһpadding�����������
		long pixels = (picSize + need)/8;
		//������ӽ�����α߳�������
		long pixel_xy = pixel_x = pixel_y = (long)Math.floor(Math.sqrt(pixels));
		//��ǰ�Ѿ�ȷ�ϵ��������ֽ���
		pixels = pixel_xy*pixel_xy;
		totalSize = pixels*8;
		
		//����ʣ��û�����е�����ʱ
		if(totalSize < (picSize + need)){
			//ʣ���ֽ���
			long restBytes = picSize + need - totalSize;
			//ÿ���ֽ���
			long bytesPerColumn = pixel_y * 8;
			//���ӵ�����
			long addColumnCnt = restBytes/bytesPerColumn;
			//ʣ��Ҫ��ӵ��ֽ���
			restBytes = (bytesPerColumn-restBytes%bytesPerColumn);
			if(restBytes == bytesPerColumn){
				restBytes = 0;
			}else{
				addColumnCnt++;
			}
			//ͳ���������Ҫ��ӵ��ֽ���
			pixel_x += addColumnCnt;
			need += restBytes;
			//�������ֽ���
			totalSize += addColumnCnt * bytesPerColumn;
		}
		
		picData.setNeed(need);
		picData.setPixel_x(pixel_x);
		picData.setPixel_y(pixel_y);
		picData.setSrcSize(srcSize);
		picData.setTotalSize(totalSize);
		
		return picData;
	}
}
