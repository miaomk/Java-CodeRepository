package com.techwells.wumei.controller;

import com.techwells.wumei.domain.Score;
import com.techwells.wumei.service.ScoreService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 积分Controller
 *
 * @author admin
 */
@RestController
public class ScoreController {

	@Resource
	private ScoreService scoreService;

	/**
	 * 添加积分
	 * @param userId 用户id
	 * @param strategyId 策略id
	 * @param relationId
	 * @param score 积分数量
	 * @return
	 */
	@RequestMapping(value = "/score/addScore")
	public ResultInfo addScore(@RequestParam("userId") Integer userId,
							   @RequestParam("strategyId") Integer strategyId,
							   @RequestParam(value = "relationId", defaultValue = "0") Integer relationId,
							   @RequestParam("score") Integer score) {
		ResultInfo rsInfo = new ResultInfo();


		if (userId == null) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (strategyId == null) {
			rsInfo.setMessage("策略ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (score == null) {
			rsInfo.setMessage("积分数额不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Score s = new Score();
		s.setUserId(userId);
		s.setStrategyId(strategyId);
		s.setRelationId(relationId);
		s.setScore(score);

		int count;
		try {
			count = scoreService.addScore(s);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加积分异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加积分成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加积分失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改积分信息
	 *
	 * @param score 积分信息
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/score/modifyScore")
	public ResultInfo modifyScore(@RequestBody Score score) {
		ResultInfo rsInfo = new ResultInfo();

		if (null == score.getScoreId()) {
			rsInfo.setMessage("积分ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count;
		try {
			count = scoreService.modifyScore(score);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改积分异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改积分失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改积分成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * 删除积分
	 * @param scoreId 积分id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/score/deleteScore")
	public ResultInfo deleteScore(@RequestParam("scoreId")Integer scoreId) {
		ResultInfo rsInfo = new ResultInfo();

		if (scoreId == null) {
			rsInfo.setMessage("积分Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count;
		try {
			count = scoreService.delScore(scoreId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除积分异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除积分成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除积分失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 查看积分详情
	 * @param scoreId 积分id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/score/getScoreById")
	public ResultInfo getScoreById(@RequestParam("scoreId")Integer scoreId) {
		ResultInfo rsInfo = new ResultInfo();

		if (scoreId == null ) {
			rsInfo.setMessage("积分ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		Score score;
		try {
			score = scoreService.getScoreByScoreId(scoreId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取积分信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (score == null) {
			rsInfo.setMessage("积分信息不存在！");
			rsInfo.setData(new Score());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(score);
		rsInfo.setMessage("获取积分成功！");
		return rsInfo;
	}

	/**
	 * @description 获取积分列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/score/getScoreList")
	public ResultInfo getScoreList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String userId = request.getParameter("userId");

		if (pageNum == null || pageNum.equals("")) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || pageSize.equals("")) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (userId != null && !userId.equals("")) {
			params.put("userId", userId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = scoreService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取积分总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("积分总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Score> scoreList;
		try {
			scoreList = scoreService.getScoreList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取积分列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (scoreList.size() == 0) {
			rsInfo.setMessage("积分列表为空！");
			rsInfo.setData(new ArrayList<Score>());
			return rsInfo;
		}
		rsInfo.setData(scoreList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取积分列表成功！");
		return rsInfo;
	}


	/**
	 * @description 获取积分列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/score/getMyScoreList")
	public ResultInfo getMyScoreList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<String, Object>();

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String userId = request.getParameter("userId");

		if (pageNum == null || pageNum.equals("")) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || pageSize.equals("")) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}else{
			params.put("userId", userId);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = scoreService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取积分总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("积分总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<Score> scoreList = null;
		try {
			scoreList = scoreService.getScoreList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取积分列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (scoreList.size() == 0) {
			rsInfo.setMessage("积分列表为空！");
			rsInfo.setData(new ArrayList<Score>());
			return rsInfo;
		}
		rsInfo.setData(scoreList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取积分列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除积分
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/score/deleteScoreBatch")
	public ResultInfo deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = scoreService.deleteBatch(idArr);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("批量删除异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (count > 0) {
			rsInfo.setMessage("批量删除成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("批量删除失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}
}
