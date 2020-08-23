package top.hxq.taotao.manage.controller.common;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import top.hxq.taotao.manage.utils.UploadUtils;
import top.hxq.taotao.manage.vo.PicUploadResult;

/**
 * 图片服务控制层
 * 
 * @author wuzm
 * @date 2020年8月22日 下午3:25:00
 */
@RequestMapping("/pic")
@Controller
public class PicController {

	// 可接收的图片类型
	private static final String[] IMAGE_TYPE = { ".jpg", ".png", ".bmp", ".jpeg", "gif" };

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 上传图片
	 * 
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(@RequestParam(value = "uploadFile") MultipartFile multipartFile) throws Exception {
		PicUploadResult picUploadResult = new PicUploadResult();
		picUploadResult.setError(1);
		try {
			// 校验图片
			boolean isLegal = false;
			for (String type : IMAGE_TYPE) {
				if (multipartFile.getOriginalFilename().indexOf(type) > 0) {
					isLegal = true;
					break;
				}
			}
			if (isLegal) {
				// 再次校验图片内容是否合法
				BufferedImage image = ImageIO.read(multipartFile.getInputStream());
				picUploadResult.setWidth(image.getWidth() + "");
				picUploadResult.setHeight(image.getHeight() + "");
				// 上传图片
				String url = UploadUtils.uploadImage(multipartFile);
				picUploadResult.setUrl(url);
				picUploadResult.setError(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回数据
		return MAPPER.writeValueAsString(picUploadResult);
	}
}
