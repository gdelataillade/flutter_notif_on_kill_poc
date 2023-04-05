// ignore_for_file: avoid_print

import 'package:flutter/services.dart';

class NotifOnKill {
  static const platform = MethodChannel(
      'com.example.flutter_notif_on_kill_poc/flutter_notif_on_kill_poc');

  static Future<void> toggleNotifOnKill(bool value) async {
    try {
      if (value) {
        await platform.invokeMethod(
          'setNotificationOnKillService',
          {
            'title': "Application killed !",
            'description': "The application just got killed.",
          },
        );
        print('NotificationOnKillService set with success');
      } else {
        await platform.invokeMethod('stopNotificationOnKillService');
        print('NotificationOnKillService stopped with success');
      }
    } catch (e) {
      print('NotificationOnKillService error: $e');
    }
  }
}
