package com.bigdata.olearn.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("mooc", "mooc_id", Mooc.class);
		arp.addMapping("mooc_cluster", "id", MoocCluster.class);
		arp.addMapping("mooc_link_cluster", "id", MoocLinkCluster.class);
		arp.addMapping("mooc_menu", "id", MoocMenu.class);
		arp.addMapping("schedule", "id", Schedule.class);
		arp.addMapping("user", "user_id", User.class);
		arp.addMapping("work", "work_id", Work.class);
		arp.addMapping("work_cluster", "id", WorkCluster.class);
	}
}

