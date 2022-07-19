import {SafeAreaView, StyleSheet} from 'react-native';

import React, {useEffect} from 'react';
import {useNavigation} from '@react-navigation/native';
import PageName from './PageName';
import AwesomeButtonRick from 'react-native-really-awesome-button/src/themes/rick';
import FullScreenBackground from '../General/FullScreenBackground';
import SignifyHeader from '../General/SignifyHeader';
import Orientation from 'react-native-orientation-locker';
const backGroundImg = require('../../../resources/images/HomePageBackground2.jpg');
import KeepAwake from 'react-native-keep-awake';

const HomePage = () => {
  const navigation = useNavigation();

  useEffect(() => {
    Orientation.lockToPortrait();
    KeepAwake.activate();
    // console.log('hey');
    return () => {
      //Orientation.unlockAllOrientations();
      //KeepAwake.deactivate()
    };
  });
  const handleLiveTranslateButtonClicked = () => {
    navigation.navigate(PageName.CameraPage);
  };
  const handleLearnSignLanguageButtonClicked = () => {
    navigation.navigate(PageName.LearningPage);
  };

  const handleSettingsButtonClicked = () => {
    navigation.navigate(PageName.SettingsPage);
  };
  //<MaterialIcons name="arrow-forward-ios" color="#fff" size={15} />
  return (
    <FullScreenBackground source={backGroundImg}>
      <SignifyHeader fontSize={80} />
      <SafeAreaView style={styles.container}>
        <AwesomeButtonRick
          type="primary"
          onPress={handleLiveTranslateButtonClicked}
          backgroundColor="yellow"
          textColor="black"
          backgroundDarker={null}>
          Live Translation
        </AwesomeButtonRick>

        <AwesomeButtonRick
          type="primary"
          onPress={handleLearnSignLanguageButtonClicked}
          backgroundColor="red"
          textColor="black"
          backgroundDarker={null}>
          Learn Sign Language
        </AwesomeButtonRick>
        <AwesomeButtonRick
          type="primary"
          onPress={handleSettingsButtonClicked}
          backgroundColor="gray"
          textColor="white"
          backgroundDarker={null}>
          Settings
        </AwesomeButtonRick>
      </SafeAreaView>
    </FullScreenBackground>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    fontSize: 30,
    fontWeight: 'bold',
    color: '#20315f',
  },
});
export default HomePage;
