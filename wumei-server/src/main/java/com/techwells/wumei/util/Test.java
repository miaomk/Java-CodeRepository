package com.techwells.wumei.util;

import com.techwells.wumei.huanxin.api.impl.EasemobIMUsers;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String groupId = "86663635206145";
//		EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
//        Object result = easemobChatGroup.getChatGroupUsers(groupId);
//        Gson gson = new Gson();
//
//        
//        GroupUser gu = gson.fromJson((String) result, GroupUser.class);
//        
//        ArrayList<Object> list=gson.fromJson(gu.getData().toString(), 
//                new TypeToken<ArrayList<Object>>(){}.getType());
//       
//        System.out.println(gu.getData().toString());
//        for(int i = 0; i< list.size(); i++) {
//        	
//        	BigDecimal  bd = null;
//        	if(i == list.size() -1) {
//        		bd = new BigDecimal(((LinkedTreeMap)list.get(i)).get("owner").toString()); 
//        		
//        	}else {
//        		bd = new BigDecimal(((LinkedTreeMap)list.get(i)).get("member").toString());
//        	}
//  
//        	
//        	 System.out.println(bd.toPlainString());
//        }
		
		
		
//		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
//
//	    RegisterUsers users = new RegisterUsers();
//	    io.swagger.client.model.User hxUser = new io.swagger.client.model.User().username("15910009999").password("123456");
//	    users.add(hxUser);
//	    Object result = easemobIMUsers.createNewIMUserSingle(users);
	    
	    
EasemobIMUsers easemobIMUsers = new EasemobIMUsers();


		
//        Object result = easemobIMUsers.getFriends("18916658600");
//        
//        JSONObject obj = JSONObject.fromObject(result);
//        
//        JSONArray arr = (JSONArray)obj.get("data");
//        
//        List<String> userNames = new ArrayList<String>();
//        
//        for(Object user: arr) {
//        	
//        	userNames.add((String ) user); 
//        }
//        
//        System.out.println(userNames.get(0));


Object result = easemobIMUsers.addFriendSingle("18916658600", "15910009999");
//Object result = easemobIMUsers.deleteFriendSingle("18916658600", "15910009999");



System.out.println(result);

	}

}
