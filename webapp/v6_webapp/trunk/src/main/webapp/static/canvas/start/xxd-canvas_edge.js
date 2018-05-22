/*jslint */
/*global AdobeEdge: false, window: false, document: false, console:false, alert: false */
(function (compId) {

    "use strict";
    var im='images/',
        aud='media/',
        vid='media/',
        js='js/',
        fonts = {
        },
        opts = {
            'gAudioPreloadPreference': 'auto',
            'gVideoPreloadPreference': 'auto'
        },
        resources = [
        ],
        scripts = [
        ],
        symbols = {
            "stage": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "both",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            id: 'bg',
                            display: 'block',
                            type: 'image',
                            rect: ['0px', '0px', '750px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"1.png",'0px','0px']
                        },
                        {
                            id: 'title',
                            display: 'none',
                            type: 'image',
                            rect: ['112px', '117px', '526px', '91px', 'auto', 'auto'],
                            overflow: 'visible',
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"psd-1_03.png",'0px','0px']
                        },
                        {
                            id: 'arrow',
                            display: 'none',
                            type: 'image',
                            rect: ['52px', '302px', '86%', '365px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"psd-1_07.png",'0px','0px'],
                            transform: [[],[],[],[],['50%','5%']]
                        },
                        {
                            id: 'cart',
                            display: 'block',
                            type: 'image',
                            rect: ['-95px', '485px', '73px', '57px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"psd-1_03_16.png",'0px','0px']
                        },
                        {
                            id: 'car',
                            display: 'block',
                            type: 'image',
                            rect: ['-112px', '268px', '107px', '103px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"psd-1_03_13.png",'0px','0px']
                        },
                        {
                            id: 'house',
                            display: 'block',
                            type: 'image',
                            rect: ['-116px', '-204px', '250px', '254px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"psd-1_03_10.png",'0px','0px']
                        },
                        {
                            id: 'running2',
                            symbolName: 'running2',
                            display: 'none',
                            type: 'rect',
                            rect: ['243px', '560px', '313', '504', 'auto', 'auto']
                        },
                        {
                            id: 'running4',
                            symbolName: 'running4',
                            display: 'block',
                            type: 'rect',
                            rect: ['82px', '793px', '637', '280', 'auto', 'auto']
                        },
                        {
                            id: 'running1',
                            symbolName: 'running1',
                            display: 'none',
                            type: 'rect',
                            rect: ['154px', '558px', '430', '475', 'auto', 'auto']
                        },
                        {
                            id: 'shadow',
                            symbolName: 'shadow',
                            display: 'none',
                            type: 'rect',
                            rect: ['306px', '1083px', '172', '15', 'auto', 'auto']
                        },
                        {
                            id: 'p2-bg1',
                            display: 'none',
                            type: 'rect',
                            rect: ['0px', '0px', '375px', '567px', 'auto', 'auto'],
                            fill: ["rgba(73,180,174,1.00)"],
                            stroke: [0,"rgba(0,0,0,1)","none"]
                        },
                        {
                            id: 'p2-bg2',
                            display: 'none',
                            type: 'rect',
                            rect: ['375px', '0px', '375px', '567px', 'auto', 'auto'],
                            fill: ["rgba(233,111,26,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: 'p2-bg3',
                            display: 'none',
                            type: 'rect',
                            rect: ['0px', '567px', '375px', '567px', 'auto', 'auto'],
                            fill: ["rgba(205,75,75,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: 'p2-bg4',
                            type: 'rect',
                            rect: ['375px', '567px', '375px', '567px', 'auto', 'auto'],
                            fill: ["rgba(131,56,141,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: '_2-2_034',
                            display: 'none',
                            type: 'image',
                            rect: ['126px', '241', '51px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_03.png",'0px','0px']
                        },
                        {
                            id: '_2-2_05',
                            display: 'none',
                            type: 'image',
                            rect: ['192px', '241', '49px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_05.png",'0px','0px']
                        },
                        {
                            id: '_2-2_072',
                            display: 'none',
                            type: 'image',
                            rect: ['259px', '241', '50px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_07.png",'0px','0px']
                        },
                        {
                            id: '_2-2_092',
                            display: 'none',
                            type: 'image',
                            rect: ['323px', '241', '52px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_09.png",'0px','0px']
                        },
                        {
                            id: '_2-2_11',
                            display: 'none',
                            type: 'image',
                            rect: ['191px', '344', '52px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_11.png",'0px','0px']
                        },
                        {
                            id: '_2-2_13',
                            display: 'none',
                            type: 'image',
                            rect: ['258px', '344px', '51px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_13.png",'0px','0px']
                        },
                        {
                            id: '_2-2_15',
                            display: 'none',
                            type: 'image',
                            rect: ['323px', '344px', '52px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_15.png",'0px','0px']
                        },
                        {
                            id: '_2-2_17',
                            display: 'none',
                            type: 'image',
                            rect: ['390px', '344px', '51px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_17.png",'0px','0px']
                        },
                        {
                            id: '_2-2_19',
                            display: 'none',
                            type: 'image',
                            rect: ['461px', '344px', '51px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_19.png",'0px','0px']
                        },
                        {
                            id: '_2-2_21',
                            display: 'none',
                            type: 'image',
                            rect: ['534px', '344px', '48px', '52px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2-2_21.png",'0px','0px']
                        },
                        {
                            id: '_2_27_03_03',
                            display: 'none',
                            type: 'image',
                            rect: ['141px', '335px', '468px', '458px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"2_27_03_03.png",'0px','0px']
                        },
                        {
                            id: '_2_03_07',
                            display: 'none',
                            type: 'image',
                            rect: ['150px', '598px', '76px', '101px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"2_03_07.png",'0px','0px']
                        },
                        {
                            id: 'cart2',
                            symbolName: 'cart',
                            display: 'none',
                            type: 'rect',
                            rect: ['-250px', '162px', '197', '256', 'auto', 'auto'],
                            opacity: '0'
                        },
                        {
                            id: '_2_03',
                            display: 'none',
                            type: 'image',
                            rect: ['64px', '64px', '61px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_03.png",'0px','0px']
                        },
                        {
                            id: '_2_05',
                            display: 'none',
                            type: 'image',
                            rect: ['168', '64', '61px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_05.png",'0px','0px']
                        },
                        {
                            id: '_2_07',
                            display: 'none',
                            type: 'image',
                            rect: ['268px', '64px', '60px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_07.png",'0px','0px']
                        },
                        {
                            id: '_2_03_03',
                            display: 'none',
                            type: 'image',
                            rect: ['179px', '137px', '93px', '67px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"2_03_03.png",'0px','0px']
                        },
                        {
                            id: '_2_23',
                            display: 'none',
                            type: 'image',
                            rect: ['464px', '-181px', '169px', '163px', 'auto', 'auto'],
                            autoOrient: false,
                            fill: ["rgba(0,0,0,0)",im+"2_23.png",'0px','0px']
                        },
                        {
                            id: '_2_09',
                            display: 'none',
                            type: 'image',
                            rect: ['445px', '67px', '59px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_09.png",'0px','0px']
                        },
                        {
                            id: '_2_10',
                            display: 'none',
                            type: 'image',
                            rect: ['535px', '67', '63px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_10.png",'0px','0px']
                        },
                        {
                            id: '_2_12',
                            type: 'image',
                            rect: ['633px', '67px', '62px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_12.png",'0px','0px']
                        },
                        {
                            id: 'computer',
                            symbolName: 'computer',
                            display: 'none',
                            type: 'rect',
                            rect: ['101px', '787px', '196', '161', 'auto', 'auto'],
                            opacity: '0',
                            transform: [[],[],[],['1.86957','1.86957']]
                        },
                        {
                            id: '_2_27_03_03_07',
                            display: 'none',
                            type: 'image',
                            rect: ['141px', '821px', '112px', '59px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_27_03_03_07.png",'0px','0px']
                        },
                        {
                            id: '_2_382',
                            display: 'none',
                            type: 'image',
                            rect: ['70px', '999px', '55px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_38.png",'0px','0px']
                        },
                        {
                            id: '_2_40',
                            display: 'none',
                            type: 'image',
                            rect: ['168px', '999', '53px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_40.png",'0px','0px']
                        },
                        {
                            id: '_2_42',
                            display: 'none',
                            type: 'image',
                            rect: ['262px', '999', '60px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_42.png",'0px','0px']
                        },
                        {
                            id: '_2_30',
                            display: 'none',
                            type: 'image',
                            rect: ['496px', '780px', '120px', '185px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"2_30.png",'0px','0px']
                        },
                        {
                            id: '_2_44',
                            display: 'none',
                            type: 'image',
                            rect: ['445px', '999px', '61px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_44.png",'0px','0px']
                        },
                        {
                            id: '_2_45',
                            display: 'none',
                            type: 'image',
                            rect: ['535px', '999px', '64px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_45.png",'0px','0px']
                        },
                        {
                            id: '_2_46',
                            display: 'none',
                            type: 'image',
                            rect: ['630px', '999', '64px', '60px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"2_46.png",'0px','0px']
                        },
                        {
                            id: 'xx14',
                            display: 'none',
                            type: 'image',
                            rect: ['393px', '531px', '22px', '20px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"xx%281%29.png",'0px','0px']
                        },
                        {
                            id: '_33',
                            display: 'none',
                            type: 'image',
                            rect: ['0px', '0px', '750px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"33.png",'0px','0px']
                        },
                        {
                            id: 'p3-ren1',
                            display: 'none',
                            type: 'image',
                            rect: ['258px', '427px', '292px', '468px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"13_03.png",'0px','0px']
                        },
                        {
                            id: 'p3-ren2',
                            display: 'none',
                            type: 'image',
                            rect: ['258px', '427px', '291px', '468px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"33_03.png",'0px','0px']
                        },
                        {
                            id: '_3_03',
                            display: 'none',
                            type: 'image',
                            rect: ['28px', '272px', '292px', '216px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"3_03.png",'0px','0px']
                        },
                        {
                            id: '_4_03',
                            display: 'none',
                            type: 'image',
                            rect: ['491px', '692px', '59px', '59px', 'auto', 'auto'],
                            autoOrient: true,
                            fill: ["rgba(0,0,0,0)",im+"4_03.png",'0px','0px']
                        },
                        {
                            id: '_42',
                            display: 'none',
                            type: 'image',
                            rect: ['0px', '0px', '750px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"42.png",'0px','0px']
                        },
                        {
                            id: '_4-f_03',
                            display: 'none',
                            type: 'image',
                            rect: ['82px', '80px', '53px', '54px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_03.png",'0px','0px']
                        },
                        {
                            id: '_4-f_04',
                            display: 'none',
                            type: 'image',
                            rect: ['144px', '80px', '55px', '54px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_04.png",'0px','0px']
                        },
                        {
                            id: '_4-f_07',
                            display: 'none',
                            type: 'image',
                            rect: ['82px', '170px', '53px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_07.png",'0px','0px']
                        },
                        {
                            id: '_4-f_08',
                            display: 'none',
                            type: 'image',
                            rect: ['144px', '170px', '55px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_08.png",'0px','0px']
                        },
                        {
                            id: '_4-f_09',
                            display: 'none',
                            type: 'image',
                            rect: ['209px', '170px', '54px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_09.png",'0px','0px']
                        },
                        {
                            id: '_4-f_10',
                            display: 'none',
                            type: 'image',
                            rect: ['272px', '170', '58px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_10.png",'0px','0px']
                        },
                        {
                            id: '_4-f_11',
                            display: 'none',
                            type: 'image',
                            rect: ['339px', '170px', '53px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_11.png",'0px','0px']
                        },
                        {
                            id: '_4-f_12',
                            display: 'none',
                            type: 'image',
                            rect: ['399px', '170px', '56px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_12.png",'0px','0px']
                        },
                        {
                            id: '_4-f_14',
                            display: 'none',
                            type: 'image',
                            rect: ['462px', '170px', '70px', '58px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_14.png",'0px','0px']
                        },
                        {
                            id: '_4-f_32',
                            display: 'none',
                            type: 'image',
                            rect: ['540px', '170px', '53px', '57px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_32.png",'0px','0px']
                        },
                        {
                            id: '_4-f_17',
                            display: 'none',
                            type: 'image',
                            rect: ['81px', '264px', '55px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_17.png",'0px','0px']
                        },
                        {
                            id: '_4-f_18',
                            display: 'none',
                            type: 'image',
                            rect: ['145px', '264', '54px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_18.png",'0px','0px']
                        },
                        {
                            id: '_4-f_19',
                            display: 'none',
                            type: 'image',
                            rect: ['209px', '264px', '58px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_19.png",'0px','0px']
                        },
                        {
                            id: '_4-f_20',
                            display: 'none',
                            type: 'image',
                            rect: ['274px', '264px', '53px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_20.png",'0px','0px']
                        },
                        {
                            id: '_4-f_21',
                            display: 'none',
                            type: 'image',
                            rect: ['336px', '264px', '56px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_21.png",'0px','0px']
                        },
                        {
                            id: '_4-f_22',
                            display: 'none',
                            type: 'image',
                            rect: ['399px', '264px', '55px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_22.png",'0px','0px']
                        },
                        {
                            id: '_4-f_23',
                            display: 'none',
                            type: 'image',
                            rect: ['465px', '264', '57px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_23.png",'0px','0px']
                        },
                        {
                            id: '_4-f_24',
                            display: 'none',
                            type: 'image',
                            rect: ['532px', '264', '54px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_24.png",'0px','0px']
                        },
                        {
                            id: '_4-f_25',
                            display: 'none',
                            type: 'image',
                            rect: ['593px', '264px', '55px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_25.png",'0px','0px']
                        },
                        {
                            id: '_4-f_26',
                            display: 'none',
                            type: 'image',
                            rect: ['658px', '264px', '57px', '55px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_26.png",'0px','0px']
                        },
                        {
                            id: '_4-f_28',
                            display: 'none',
                            type: 'image',
                            rect: ['82px', '358px', '53px', '57px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_28.png",'0px','0px']
                        },
                        {
                            id: '_4-f_29',
                            display: 'none',
                            type: 'image',
                            rect: ['144px', '358px', '55px', '57px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_29.png",'0px','0px']
                        },
                        {
                            id: '_4-f_30',
                            display: 'none',
                            type: 'image',
                            rect: ['212px', '358px', '54px', '57px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_30.png",'0px','0px']
                        },
                        {
                            id: '_4-f_31',
                            display: 'none',
                            type: 'image',
                            rect: ['274px', '358px', '58px', '57px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4-f_31.png",'0px','0px']
                        },
                        {
                            id: '_4_07',
                            display: 'none',
                            type: 'image',
                            rect: ['256px', '376px', '238px', '238px', 'auto', 'auto'],
                            autoOrient: false,
                            fill: ["rgba(0,0,0,0)",im+"4_07.png",'0px','0px']
                        },
                        {
                            id: '_4_11',
                            display: 'none',
                            type: 'image',
                            rect: ['209px', '660px', '349px', '359px', 'auto', 'auto'],
                            opacity: '1',
                            fill: ["rgba(0,0,0,0)",im+"4_11.png",'0px','0px']
                        },
                        {
                            id: '_4_122222227',
                            display: 'none',
                            type: 'image',
                            rect: ['282px', '1001px', '214px', '41px', 'auto', 'auto'],
                            opacity: '1',
                            fill: ["rgba(0,0,0,0)",im+"4_122222227.png",'0px','0px']
                        },
                        {
                            id: '_4_132',
                            display: 'none',
                            type: 'image',
                            rect: ['221px', '656px', '326px', '393px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"4_132.png",'0px','0px']
                        },
                        {
                            id: 'Rectangle',
                            display: 'none',
                            type: 'rect',
                            rect: ['0px', '0px', '750px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(192,192,192,1)",[270,[['rgba(249,255,213,1.00)',0],['rgba(96,198,217,1.00)',100]]]],
                            stroke: [0,"rgba(0,0,0,1)","none"]
                        },
                        {
                            id: '_5_032',
                            display: 'none',
                            type: 'image',
                            rect: ['304px', '794px', '142px', '159px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_03.png",'0px','0px']
                        },
                        {
                            id: '_5_06',
                            display: 'none',
                            type: 'image',
                            rect: ['308px', '784px', '133px', '152px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_06.png",'0px','0px']
                        },
                        {
                            id: '_5_10',
                            display: 'none',
                            type: 'image',
                            rect: ['312px', '784px', '135px', '157px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_10.png",'0px','0px']
                        },
                        {
                            id: '_5_14',
                            display: 'none',
                            type: 'image',
                            rect: ['308px', '782px', '133px', '158px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_14.png",'0px','0px']
                        },
                        {
                            id: '_5_16',
                            display: 'none',
                            type: 'image',
                            rect: ['314px', '765px', '133px', '165px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_16.png",'0px','0px']
                        },
                        {
                            id: '_5_19',
                            display: 'none',
                            type: 'image',
                            rect: ['310px', '771px', '130px', '154px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_19.png",'0px','0px']
                        },
                        {
                            id: 'Ellipse',
                            display: 'none',
                            type: 'ellipse',
                            rect: ['164px', '656px', '422px', '422px', 'auto', 'auto'],
                            borderRadius: ["50%", "50%", "50%", "50%"],
                            fill: ["rgba(255,250,211,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: '_4_072',
                            display: 'none',
                            type: 'image',
                            rect: ['428px', '418px', '238px', '238px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4_072.png",'0px','0px']
                        },
                        {
                            id: '_4_0722',
                            display: 'none',
                            type: 'image',
                            rect: ['245px', '457px', '157px', '157px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"4_072.png",'0px','0px']
                        },
                        {
                            id: 'Rectangle5',
                            display: 'none',
                            type: 'rect',
                            rect: ['372px', '694px', '4px', '173px', 'auto', 'auto'],
                            fill: ["rgba(69,68,61,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"],
                            transform: [[],['360'],[],[],['50%','100%']]
                        },
                        {
                            id: 'Rectangle6',
                            display: 'none',
                            type: 'rect',
                            rect: ['372px', '677px', '4px', '189px', 'auto', 'auto'],
                            borderRadius: ["0px", "0px", "0px", "0px"],
                            fill: ["rgba(69,68,61,1)"],
                            stroke: [0,"rgb(0, 0, 0)","none"],
                            transform: [[],['2880'],[],[],['50%','100%']]
                        },
                        {
                            id: '_5_28',
                            display: 'none',
                            type: 'image',
                            rect: ['183px', '656px', '384px', '384px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"5_28.png",'0px','0px']
                        },
                        {
                            id: '_5f_03',
                            display: 'none',
                            type: 'image',
                            rect: ['72px', '132px', '605px', '55px', 'auto', 'auto'],
                            opacity: '1',
                            fill: ["rgba(0,0,0,0)",im+"5f_03.png",'0px','0px']
                        },
                        {
                            id: '_5f_062',
                            display: 'none',
                            type: 'image',
                            rect: ['45px', '132px', '659px', '56px', 'auto', 'auto'],
                            opacity: '1',
                            fill: ["rgba(0,0,0,0)",im+"5f_06.png",'0px','0px']
                        },
                        {
                            id: '_5f_09',
                            display: 'none',
                            type: 'image',
                            rect: ['29px', '135px', '692px', '53px', 'auto', 'auto'],
                            opacity: '1',
                            fill: ["rgba(0,0,0,0)",im+"5f_09.png",'0px','0px']
                        },
                        {
                            id: 'Rectangle7',
                            display: 'none',
                            type: 'rect',
                            rect: ['0px', '0px', '750px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(251,248,207,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: '_6_032',
                            display: 'none',
                            type: 'image',
                            rect: ['29px', '260px', '692px', '796px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"6_032.png",'0px','0px']
                        },
                        {
                            id: '_6_03_032',
                            display: 'none',
                            type: 'image',
                            rect: ['74px', '44px', '602px', '197px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"6_03_032.png",'0px','0px']
                        },
                        {
                            id: '_6_06',
                            display: 'none',
                            type: 'image',
                            rect: ['390px', '760px', '288px', '312px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"6_06.png",'0px','0px']
                        },
                        {
                            id: '_6-1_03',
                            display: 'none',
                            type: 'image',
                            rect: ['450px', '832px', '226px', '233px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"6-1_03.png",'0px','0px','100%','100%', 'no-repeat']
                        },
                        {
                            id: 'Rectangle2',
                            display: 'none',
                            type: 'rect',
                            rect: ['0px', '0px', '749px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(55,195,253,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: '_7_03',
                            display: 'block',
                            type: 'image',
                            rect: ['80px', '118px', '295px', '294px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"7_03.png",'0px','0px']
                        },
                        {
                            id: '_7_06',
                            display: 'none',
                            type: 'image',
                            rect: ['424px', '217px', '275px', '96px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"7_06.png",'0px','0px']
                        },
                        {
                            id: '_7_11',
                            display: 'block',
                            type: 'image',
                            rect: ['414px', '446px', '295px', '295px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"7_11.png",'0px','0px']
                        },
                        {
                            id: '_7_14',
                            display: 'none',
                            type: 'image',
                            rect: ['91px', '545px', '274px', '97px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"7_14.png",'0px','0px']
                        },
                        {
                            id: '_7_19',
                            display: 'block',
                            type: 'image',
                            rect: ['78px', '746px', '297px', '297px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"7_19.png",'0px','0px'],
                            transform: [[],[],[],['3.5','3.5']]
                        },
                        {
                            id: '_7_22',
                            display: 'none',
                            type: 'image',
                            rect: ['424px', '846px', '275px', '97px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"7_22.png",'0px','0px']
                        },
                        {
                            id: 'Rectangle3',
                            display: 'none',
                            type: 'rect',
                            rect: ['0px', '0px', '750px', '1134px', 'auto', 'auto'],
                            fill: ["rgba(249,244,207,1.00)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: '_91_06',
                            display: 'none',
                            type: 'image',
                            rect: ['0px', '10px', '750px', '871px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"9%281%29_06.png",'0px','0px']
                        },
                        {
                            id: '_911_022',
                            display: 'none',
                            type: 'image',
                            rect: ['0px', '953px', '750px', '181px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"9%2811%29_02.png",'0px','0px']
                        },
                        {
                            id: '_91_03',
                            display: 'none',
                            type: 'image',
                            rect: ['322px', '698px', '153px', '313px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"9%281%29_03.png",'0px','0px']
                        },
                        {
                            id: '_91_07',
                            display: 'none',
                            type: 'image',
                            rect: ['0px', '1002px', '750px', '132px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"9%281%29_07.png",'0px','0px']
                        },
                        {
                            id: '_10_03',
                            display: 'none',
                            type: 'image',
                            rect: ['220px', '238px', '310px', '329px', 'auto', 'auto'],
                            opacity: '0',
                            fill: ["rgba(0,0,0,0)",im+"10_03.png",'0px','0px']
                        },
                        {
                            id: '_10_07',
                            display: 'none',
                            type: 'image',
                            rect: ['375px', '598px', '332px', '122px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"10_07.png",'0px','0px']
                        },
                        {
                            id: 'Rectangle9',
                            display: 'none',
                            type: 'rect',
                            rect: ['370px', '598px', '340px', '122px', 'auto', 'auto'],
                            fill: ["rgba(255,255,255,1)"],
                            stroke: [0,"rgb(0, 0, 0)","none"]
                        },
                        {
                            id: '_10a1_03',
                            display: 'none',
                            type: 'image',
                            rect: ['218px', '856px', '290px', '85px', 'auto', 'auto'],
                            fill: ["rgba(0,0,0,0)",im+"10a%281%29_03.png",'0px','0px']
                        }
                    ],
                    style: {
                        '${Stage}': {
                            isStage: true,
                            rect: ['null', 'null', '750px', '1134px', 'auto', 'auto'],
                            overflow: 'hidden',
                            fill: ["rgba(255,255,255,1.00)"]
                        }
                    }
                },
                timeline: {
                    duration: 34500,
                    autoPlay: true,
                    data: [
                        [
                            "eid505",
                            "display",
                            14900,
                            0,
                            "linear",
                            "${_4-f_14}",
                            'none',
                            'block'
                        ],
                        [
                            "eid583",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_14}",
                            'block',
                            'none'
                        ],
                        [
                            "eid641",
                            "location",
                            21604,
                            396,
                            "linear",
                            "${_5_19}",
                            [[375, 848, 0, 0, 0, 0,0],[647, 714, 0, 0, 0, 0,303.22]]
                        ],
                        [
                            "eid642",
                            "location",
                            23000,
                            333,
                            "linear",
                            "${_5_19}",
                            [[647, 714, 0, 0, 0, 0,0],[375, 848, 0, 0, 0, 0,303.22]]
                        ],
                        [
                            "eid198",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${car}",
                            'block',
                            'none'
                        ],
                        [
                            "eid470",
                            "display",
                            11800,
                            0,
                            "linear",
                            "${_4_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid492",
                            "display",
                            13900,
                            0,
                            "linear",
                            "${_4_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid511",
                            "display",
                            15500,
                            0,
                            "linear",
                            "${_4-f_21}",
                            'none',
                            'block'
                        ],
                        [
                            "eid577",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_21}",
                            'block',
                            'none'
                        ],
                        [
                            "eid232",
                            "display",
                            6100,
                            0,
                            "linear",
                            "${_2-2_05}",
                            'none',
                            'block'
                        ],
                        [
                            "eid251",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_05}",
                            'block',
                            'none'
                        ],
                        [
                            "eid602",
                            "location",
                            20200,
                            948,
                            "linear",
                            "${_5f_03}",
                            [[374.5, 159.5, 0, 0, 0, 0,0],[374.5, -49.83, 0, 0, 0, 0,209.33]]
                        ],
                        [
                            "eid754",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_7_19}",
                            'block',
                            'none'
                        ],
                        [
                            "eid717",
                            "scaleX",
                            28000,
                            302,
                            "linear",
                            "${_6-1_03}",
                            '3',
                            '1'
                        ],
                        [
                            "eid764",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_91_06}",
                            'none',
                            'block'
                        ],
                        [
                            "eid782",
                            "display",
                            33400,
                            0,
                            "linear",
                            "${_91_06}",
                            'block',
                            'none'
                        ],
                        [
                            "eid680",
                            "display",
                            25159,
                            0,
                            "linear",
                            "${_4_0722}",
                            'none',
                            'block'
                        ],
                        [
                            "eid692",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${_4_0722}",
                            'block',
                            'none'
                        ],
                        [
                            "eid638",
                            "display",
                            21604,
                            0,
                            "linear",
                            "${_5_19}",
                            'none',
                            'block'
                        ],
                        [
                            "eid643",
                            "display",
                            23333,
                            0,
                            "linear",
                            "${_5_19}",
                            'block',
                            'none'
                        ],
                        [
                            "eid562",
                            "opacity",
                            17429,
                            821,
                            "linear",
                            "${_4_122222227}",
                            '1',
                            '0'
                        ],
                        [
                            "eid636",
                            "display",
                            21450,
                            0,
                            "linear",
                            "${_5_16}",
                            'none',
                            'block'
                        ],
                        [
                            "eid645",
                            "display",
                            23500,
                            0,
                            "linear",
                            "${_5_16}",
                            'block',
                            'none'
                        ],
                        [
                            "eid697",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${Rectangle7}",
                            'none',
                            'block'
                        ],
                        [
                            "eid726",
                            "display",
                            29000,
                            0,
                            "linear",
                            "${Rectangle7}",
                            'block',
                            'none'
                        ],
                        [
                            "eid664",
                            "display",
                            22647,
                            0,
                            "linear",
                            "${Rectangle5}",
                            'none',
                            'block'
                        ],
                        [
                            "eid691",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${Rectangle5}",
                            'block',
                            'none'
                        ],
                        [
                            "eid285",
                            "display",
                            8068,
                            0,
                            "linear",
                            "${_2_23}",
                            'none',
                            'block'
                        ],
                        [
                            "eid417",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_23}",
                            'block',
                            'none'
                        ],
                        [
                            "eid14",
                            "display",
                            1000,
                            0,
                            "linear",
                            "${title}",
                            'none',
                            'block'
                        ],
                        [
                            "eid201",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${title}",
                            'block',
                            'none'
                        ],
                        [
                            "eid69",
                            "location",
                            2000,
                            250,
                            "easeInElastic",
                            "${cart}",
                            [[-57.17, 461.5, 0, 0, 0, 0,0],[69.5, 576.5, 0, 0, 0, 0,171.09]]
                        ],
                        [
                            "eid515",
                            "display",
                            15800,
                            0,
                            "linear",
                            "${_4-f_24}",
                            'none',
                            'block'
                        ],
                        [
                            "eid574",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_24}",
                            'block',
                            'none'
                        ],
                        [
                            "eid786",
                            "opacity",
                            33400,
                            400,
                            "linear",
                            "${_10_03}",
                            '0',
                            '1'
                        ],
                        [
                            "eid271",
                            "opacity",
                            7700,
                            200,
                            "linear",
                            "${cart2}",
                            '0',
                            '1'
                        ],
                        [
                            "eid314",
                            "left",
                            8600,
                            0,
                            "linear",
                            "${_2_09}",
                            '445px',
                            '445px'
                        ],
                        [
                            "eid624",
                            "location",
                            20700,
                            448,
                            "linear",
                            "${_5_032}",
                            [[375, 873.5, 0, 0, 0, 0,0],[112, 646.5, 0, 0, 0, 0,347.42]]
                        ],
                        [
                            "eid651",
                            "location",
                            23854,
                            327,
                            "linear",
                            "${_5_032}",
                            [[112, 646.5, 0, 0, 0, 0,0],[370, 872.5, 0, 0, 0, 0,342.99]]
                        ],
                        [
                            "eid234",
                            "display",
                            6300,
                            0,
                            "linear",
                            "${_2-2_092}",
                            'none',
                            'block'
                        ],
                        [
                            "eid249",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_092}",
                            'block',
                            'none'
                        ],
                        [
                            "eid522",
                            "display",
                            16322,
                            0,
                            "linear",
                            "${_4_11}",
                            'none',
                            'block'
                        ],
                        [
                            "eid567",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4_11}",
                            'block',
                            'none'
                        ],
                        [
                            "eid205",
                            "background-color",
                            6000,
                            0,
                            "linear",
                            "${Stage}",
                            'rgba(255,255,255,1.00)',
                            'rgba(255,255,255,1.00)'
                        ],
                        [
                            "eid276",
                            "display",
                            7800,
                            0,
                            "linear",
                            "${_2_05}",
                            'none',
                            'block'
                        ],
                        [
                            "eid420",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_05}",
                            'block',
                            'none'
                        ],
                        [
                            "eid315",
                            "left",
                            8600,
                            0,
                            "linear",
                            "${_2_10}",
                            '535px',
                            '535px'
                        ],
                        [
                            "eid778",
                            "location",
                            31701,
                            1000,
                            "linear",
                            "${_91_03}",
                            [[398.5, 854.5, 0, 0, 0, 0,0],[422.5, 284.5, 0, 0, 0, 0,570.51]]
                        ],
                        [
                            "eid654",
                            "display",
                            19250,
                            0,
                            "linear",
                            "${Ellipse}",
                            'none',
                            'block'
                        ],
                        [
                            "eid694",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${Ellipse}",
                            'block',
                            'none'
                        ],
                        [
                            "eid521",
                            "display",
                            16700,
                            0,
                            "linear",
                            "${_4-f_31}",
                            'none',
                            'block'
                        ],
                        [
                            "eid568",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_31}",
                            'block',
                            'none'
                        ],
                        [
                            "eid729",
                            "scaleY",
                            29100,
                            200,
                            "linear",
                            "${_7_03}",
                            '3.5',
                            '1'
                        ],
                        [
                            "eid431",
                            "display",
                            10633,
                            0,
                            "easeInElastic",
                            "${_33}",
                            'none',
                            'block'
                        ],
                        [
                            "eid495",
                            "display",
                            13900,
                            0,
                            "easeInElastic",
                            "${_33}",
                            'block',
                            'none'
                        ],
                        [
                            "eid818",
                            "left",
                            33800,
                            700,
                            "linear",
                            "${Rectangle9}",
                            '370px',
                            '710px'
                        ],
                        [
                            "eid509",
                            "display",
                            15300,
                            0,
                            "linear",
                            "${_4-f_19}",
                            'none',
                            'block'
                        ],
                        [
                            "eid579",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_19}",
                            'block',
                            'none'
                        ],
                        [
                            "eid751",
                            "opacity",
                            30100,
                            200,
                            "linear",
                            "${_7_19}",
                            '0',
                            '1'
                        ],
                        [
                            "eid100",
                            "location",
                            3017,
                            233,
                            "linear",
                            "${house}",
                            [[9, -77, 0, 0, 0, 0,0],[431, 445, 0, 0, 0, 0,671.24]]
                        ],
                        [
                            "eid210",
                            "height",
                            6000,
                            0,
                            "linear",
                            "${Stage}",
                            '1134px',
                            '1134px'
                        ],
                        [
                            "eid351",
                            "opacity",
                            9020,
                            180,
                            "linear",
                            "${_2_30}",
                            '0',
                            '1'
                        ],
                        [
                            "eid496",
                            "display",
                            13900,
                            0,
                            "linear",
                            "${_42}",
                            'none',
                            'block'
                        ],
                        [
                            "eid592",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_42}",
                            'block',
                            'none'
                        ],
                        [
                            "eid768",
                            "height",
                            31701,
                            1000,
                            "linear",
                            "${_911_022}",
                            '181px',
                            '777px'
                        ],
                        [
                            "eid615",
                            "display",
                            23333,
                            0,
                            "linear",
                            "${_5f_09}",
                            'none',
                            'block'
                        ],
                        [
                            "eid621",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${_5f_09}",
                            'block',
                            'none'
                        ],
                        [
                            "eid342",
                            "display",
                            8820,
                            0,
                            "linear",
                            "${_2_382}",
                            'none',
                            'block'
                        ],
                        [
                            "eid411",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_382}",
                            'block',
                            'none'
                        ],
                        [
                            "eid354",
                            "display",
                            9400,
                            0,
                            "linear",
                            "${_2_46}",
                            'none',
                            'block'
                        ],
                        [
                            "eid405",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_46}",
                            'block',
                            'none'
                        ],
                        [
                            "eid629",
                            "location",
                            20852,
                            396,
                            "linear",
                            "${_5_06}",
                            [[374.5, 860, 0, 0, 0, 0,0],[164.5, 446, 0, 0, 0, 0,464.22]]
                        ],
                        [
                            "eid649",
                            "location",
                            23704,
                            296,
                            "linear",
                            "${_5_06}",
                            [[164.5, 446, 0, 0, 0, 0,0],[374.5, 864, 0, 0, 0, 0,467.79]]
                        ],
                        [
                            "eid758",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_7_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid787",
                            "display",
                            33800,
                            0,
                            "linear",
                            "${_10_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid733",
                            "scaleX",
                            29598,
                            200,
                            "linear",
                            "${_7_11}",
                            '3.5',
                            '1'
                        ],
                        [
                            "eid296",
                            "location",
                            8068,
                            182,
                            "linear",
                            "${_2_23}",
                            [[548.5, -99.5, 0, 0, 0, 0,0],[546.5, 262.5, 0, 0, 0, 0,362.01]]
                        ],
                        [
                            "eid303",
                            "location",
                            8250,
                            99,
                            "linear",
                            "${_2_23}",
                            [[546.5, 262.5, 0, 0, 0, 0,0],[550.5, 314.5, 0, 0, 0, 0,52.15]]
                        ],
                        [
                            "eid305",
                            "location",
                            8349,
                            98,
                            "linear",
                            "${_2_23}",
                            [[550.5, 314.5, 0, 0, 0, 0,0],[548.5, 262.5, 0, 0, 0, 0,52.04]]
                        ],
                        [
                            "eid231",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${_2-2_034}",
                            'none',
                            'block'
                        ],
                        [
                            "eid252",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_034}",
                            'block',
                            'none'
                        ],
                        [
                            "eid309",
                            "display",
                            8400,
                            0,
                            "linear",
                            "${_2_09}",
                            'none',
                            'block'
                        ],
                        [
                            "eid416",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_09}",
                            'block',
                            'none'
                        ],
                        [
                            "eid558",
                            "display",
                            17429,
                            0,
                            "linear",
                            "${_4_132}",
                            'none',
                            'block'
                        ],
                        [
                            "eid565",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4_132}",
                            'block',
                            'none'
                        ],
                        [
                            "eid469",
                            "opacity",
                            11600,
                            200,
                            "easeInElastic",
                            "${_3_03}",
                            '0',
                            '1'
                        ],
                        [
                            "eid631",
                            "location",
                            21037,
                            331,
                            "linear",
                            "${_5_10}",
                            [[379.5, 862.5, 0, 0, 0, 0,0],[341.5, 504.5, 0, 0, 0, 0,360.01]]
                        ],
                        [
                            "eid648",
                            "location",
                            23500,
                            354,
                            "linear",
                            "${_5_10}",
                            [[341.5, 504.5, 0, 0, 0, 0,0],[379.5, 866.5, 0, 0, 0, 0,363.99]]
                        ],
                        [
                            "eid737",
                            "display",
                            29798,
                            0,
                            "linear",
                            "${_7_14}",
                            'none',
                            'block'
                        ],
                        [
                            "eid755",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_7_14}",
                            'block',
                            'none'
                        ],
                        [
                            "eid280",
                            "opacity",
                            7900,
                            168,
                            "linear",
                            "${_2_03_03}",
                            '0',
                            '1'
                        ],
                        [
                            "eid663",
                            "display",
                            21148,
                            0,
                            "linear",
                            "${Rectangle6}",
                            'none',
                            'block'
                        ],
                        [
                            "eid690",
                            "display",
                            24801,
                            0,
                            "linear",
                            "${Rectangle6}",
                            'block',
                            'none'
                        ],
                        [
                            "eid732",
                            "display",
                            29300,
                            0,
                            "linear",
                            "${_7_06}",
                            'none',
                            'block'
                        ],
                        [
                            "eid757",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_7_06}",
                            'block',
                            'none'
                        ],
                        [
                            "eid242",
                            "display",
                            6900,
                            0,
                            "linear",
                            "${_2-2_21}",
                            'none',
                            'block'
                        ],
                        [
                            "eid243",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_21}",
                            'block',
                            'none'
                        ],
                        [
                            "eid262",
                            "opacity",
                            7500,
                            200,
                            "linear",
                            "${_2_03_07}",
                            '0',
                            '1'
                        ],
                        [
                            "eid227",
                            "background-color",
                            6000,
                            0,
                            "linear",
                            "${p2-bg1}",
                            'rgba(73,180,174,1.00)',
                            'rgba(73,180,174,1.00)'
                        ],
                        [
                            "eid202",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${bg}",
                            'block',
                            'none'
                        ],
                        [
                            "eid343",
                            "display",
                            8920,
                            0,
                            "linear",
                            "${_2_40}",
                            'none',
                            'block'
                        ],
                        [
                            "eid410",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_40}",
                            'block',
                            'none'
                        ],
                        [
                            "eid518",
                            "display",
                            16400,
                            0,
                            "linear",
                            "${_4-f_28}",
                            'none',
                            'block'
                        ],
                        [
                            "eid571",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_28}",
                            'block',
                            'none'
                        ],
                        [
                            "eid520",
                            "display",
                            16600,
                            0,
                            "linear",
                            "${_4-f_30}",
                            'none',
                            'block'
                        ],
                        [
                            "eid569",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_30}",
                            'block',
                            'none'
                        ],
                        [
                            "eid605",
                            "opacity",
                            20200,
                            948,
                            "linear",
                            "${_5f_03}",
                            '1',
                            '0.20325203252033'
                        ],
                        [
                            "eid275",
                            "display",
                            7700,
                            0,
                            "linear",
                            "${_2_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid421",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid282",
                            "display",
                            7469,
                            0,
                            "linear",
                            "${p2-bg3}",
                            'none',
                            'block'
                        ],
                        [
                            "eid229",
                            "display",
                            8669,
                            0,
                            "linear",
                            "${p2-bg3}",
                            'block',
                            'block'
                        ],
                        [
                            "eid426",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${p2-bg3}",
                            'block',
                            'none'
                        ],
                        [
                            "eid679",
                            "display",
                            24764,
                            0,
                            "linear",
                            "${_4_072}",
                            'none',
                            'block'
                        ],
                        [
                            "eid693",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${_4_072}",
                            'block',
                            'none'
                        ],
                        [
                            "eid698",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${_6_032}",
                            'none',
                            'block'
                        ],
                        [
                            "eid725",
                            "display",
                            29000,
                            0,
                            "linear",
                            "${_6_032}",
                            'block',
                            'none'
                        ],
                        [
                            "eid272",
                            "display",
                            7700,
                            0,
                            "linear",
                            "${cart2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid422",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${cart2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid199",
                            "display",
                            6000,
                            0,
                            "easeInElastic",
                            "${cart}",
                            'block',
                            'none'
                        ],
                        [
                            "eid236",
                            "display",
                            6500,
                            0,
                            "linear",
                            "${_2-2_13}",
                            'none',
                            'block'
                        ],
                        [
                            "eid247",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_13}",
                            'block',
                            'none'
                        ],
                        [
                            "eid597",
                            "display",
                            19250,
                            0,
                            "linear",
                            "${_5f_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid606",
                            "display",
                            21148,
                            0,
                            "linear",
                            "${_5f_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid284",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${p2-bg1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid211",
                            "display",
                            8500,
                            0,
                            "linear",
                            "${p2-bg1}",
                            'block',
                            'block'
                        ],
                        [
                            "eid428",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${p2-bg1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid504",
                            "display",
                            14800,
                            0,
                            "linear",
                            "${_4-f_12}",
                            'none',
                            'block'
                        ],
                        [
                            "eid584",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_12}",
                            'block',
                            'none'
                        ],
                        [
                            "eid699",
                            "display",
                            26500,
                            0,
                            "linear",
                            "${_6_06}",
                            'none',
                            'block'
                        ],
                        [
                            "eid719",
                            "display",
                            28000,
                            0,
                            "linear",
                            "${_6_06}",
                            'block',
                            'none'
                        ],
                        [
                            "eid609",
                            "location",
                            21911,
                            839,
                            "linear",
                            "${_5f_062}",
                            [[374.5, 160, 0, 0, 0, 0,0],[374.5, -50, 0, 0, 0, 0,210]]
                        ],
                        [
                            "eid635",
                            "location",
                            21248,
                            356,
                            "linear",
                            "${_5_14}",
                            [[374.5, 861, 0, 0, 0, 0,0],[500.5, 323, 0, 0, 0, 0,552.56]]
                        ],
                        [
                            "eid646",
                            "location",
                            23333,
                            371,
                            "linear",
                            "${_5_14}",
                            [[500.5, 323, 0, 0, 0, 0,0],[374.5, 859, 0, 0, 0, 0,550.61]]
                        ],
                        [
                            "eid508",
                            "display",
                            15200,
                            0,
                            "linear",
                            "${_4-f_18}",
                            'none',
                            'block'
                        ],
                        [
                            "eid580",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_18}",
                            'block',
                            'none'
                        ],
                        [
                            "eid344",
                            "display",
                            9020,
                            0,
                            "linear",
                            "${_2_42}",
                            'none',
                            'block'
                        ],
                        [
                            "eid409",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_42}",
                            'block',
                            'none'
                        ],
                        [
                            "eid241",
                            "display",
                            6800,
                            0,
                            "linear",
                            "${_2-2_19}",
                            'none',
                            'block'
                        ],
                        [
                            "eid245",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_19}",
                            'block',
                            'none'
                        ],
                        [
                            "eid499",
                            "display",
                            14300,
                            0,
                            "linear",
                            "${_4-f_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid589",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_07}",
                            'block',
                            'none'
                        ],
                        [
                            "eid632",
                            "display",
                            21248,
                            0,
                            "linear",
                            "${_5_14}",
                            'none',
                            'block'
                        ],
                        [
                            "eid647",
                            "display",
                            23704,
                            0,
                            "linear",
                            "${_5_14}",
                            'block',
                            'none'
                        ],
                        [
                            "eid819",
                            "width",
                            33800,
                            700,
                            "linear",
                            "${Rectangle9}",
                            '340px',
                            '0px'
                        ],
                        [
                            "eid523",
                            "display",
                            16322,
                            0,
                            "linear",
                            "${_4_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid564",
                            "display",
                            17429,
                            0,
                            "linear",
                            "${_4_07}",
                            'block',
                            'none'
                        ],
                        [
                            "eid822",
                            "left",
                            34500,
                            0,
                            "linear",
                            "${_10a1_03}",
                            '218px',
                            '218px'
                        ],
                        [
                            "eid821",
                            "display",
                            34500,
                            0,
                            "linear",
                            "${_10a1_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid507",
                            "display",
                            15100,
                            0,
                            "linear",
                            "${_4-f_17}",
                            'none',
                            'block'
                        ],
                        [
                            "eid581",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_17}",
                            'block',
                            'none'
                        ],
                        [
                            "eid256",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2_27_03_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid424",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_27_03_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid734",
                            "scaleY",
                            29598,
                            200,
                            "linear",
                            "${_7_11}",
                            '3.5',
                            '1'
                        ],
                        [
                            "eid501",
                            "display",
                            14500,
                            0,
                            "linear",
                            "${_4-f_09}",
                            'none',
                            'block'
                        ],
                        [
                            "eid587",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_09}",
                            'block',
                            'none'
                        ],
                        [
                            "eid771",
                            "display",
                            31701,
                            0,
                            "linear",
                            "${_91_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid780",
                            "display",
                            33400,
                            0,
                            "linear",
                            "${_91_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid332",
                            "scaleY",
                            8600,
                            220,
                            "linear",
                            "${computer}",
                            '1.86957',
                            '1.01242'
                        ],
                        [
                            "eid240",
                            "display",
                            6700,
                            0,
                            "linear",
                            "${_2-2_17}",
                            'none',
                            'block'
                        ],
                        [
                            "eid244",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_17}",
                            'block',
                            'none'
                        ],
                        [
                            "eid817",
                            "display",
                            33800,
                            0,
                            "linear",
                            "${Rectangle9}",
                            'none',
                            'block'
                        ],
                        [
                            "eid614",
                            "display",
                            20700,
                            0,
                            "linear",
                            "${_5f_062}",
                            'none',
                            'block'
                        ],
                        [
                            "eid612",
                            "display",
                            21911,
                            0,
                            "linear",
                            "${_5f_062}",
                            'block',
                            'block'
                        ],
                        [
                            "eid613",
                            "display",
                            22750,
                            0,
                            "linear",
                            "${_5f_062}",
                            'block',
                            'none'
                        ],
                        [
                            "eid519",
                            "display",
                            16500,
                            0,
                            "linear",
                            "${_4-f_29}",
                            'none',
                            'block'
                        ],
                        [
                            "eid570",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_29}",
                            'block',
                            'none'
                        ],
                        [
                            "eid345",
                            "display",
                            8820,
                            0,
                            "linear",
                            "${_2_27_03_03_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid412",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_27_03_03_07}",
                            'block',
                            'none'
                        ],
                        [
                            "eid513",
                            "display",
                            15700,
                            0,
                            "linear",
                            "${_4-f_23}",
                            'none',
                            'block'
                        ],
                        [
                            "eid575",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_23}",
                            'block',
                            'none'
                        ],
                        [
                            "eid328",
                            "display",
                            8600,
                            0,
                            "linear",
                            "${computer}",
                            'none',
                            'block'
                        ],
                        [
                            "eid413",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${computer}",
                            'block',
                            'none'
                        ],
                        [
                            "eid709",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${_6_03_032}",
                            'none',
                            'block'
                        ],
                        [
                            "eid724",
                            "display",
                            29000,
                            0,
                            "linear",
                            "${_6_03_032}",
                            'block',
                            'none'
                        ],
                        [
                            "eid765",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${Rectangle3}",
                            'none',
                            'block'
                        ],
                        [
                            "eid783",
                            "display",
                            33400,
                            0,
                            "linear",
                            "${Rectangle3}",
                            'block',
                            'none'
                        ],
                        [
                            "eid752",
                            "display",
                            30300,
                            0,
                            "linear",
                            "${_7_22}",
                            'none',
                            'block'
                        ],
                        [
                            "eid753",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_7_22}",
                            'block',
                            'none'
                        ],
                        [
                            "eid390",
                            "display",
                            9400,
                            0,
                            "linear",
                            "${xx14}",
                            'none',
                            'block'
                        ],
                        [
                            "eid404",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${xx14}",
                            'block',
                            'none'
                        ],
                        [
                            "eid563",
                            "opacity",
                            17429,
                            821,
                            "linear",
                            "${_4_11}",
                            '1',
                            '0'
                        ],
                        [
                            "eid283",
                            "display",
                            7378,
                            0,
                            "linear",
                            "${p2-bg2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid228",
                            "display",
                            8578,
                            0,
                            "linear",
                            "${p2-bg2}",
                            'block',
                            'block'
                        ],
                        [
                            "eid427",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${p2-bg2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid637",
                            "location",
                            21450,
                            339,
                            "linear",
                            "${_5_16}",
                            [[380.5, 847.5, 0, 0, 0, 0,0],[566.5, 521.5, 0, 0, 0, 0,375.33]]
                        ],
                        [
                            "eid644",
                            "location",
                            23172,
                            328,
                            "linear",
                            "${_5_16}",
                            [[566.5, 521.5, 0, 0, 0, 0,0],[384.5, 847.5, 0, 0, 0, 0,373.36]]
                        ],
                        [
                            "eid756",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${_7_11}",
                            'block',
                            'none'
                        ],
                        [
                            "eid747",
                            "scaleX",
                            30100,
                            200,
                            "linear",
                            "${_7_19}",
                            '3.5',
                            '1'
                        ],
                        [
                            "eid503",
                            "display",
                            14700,
                            0,
                            "linear",
                            "${_4-f_11}",
                            'none',
                            'block'
                        ],
                        [
                            "eid585",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_11}",
                            'block',
                            'none'
                        ],
                        [
                            "eid80",
                            "location",
                            2250,
                            69,
                            "linear",
                            "${arrow}",
                            [[355.49, 338.25, 0, 0, 0, 0,0],[384.1, 369.57, 0, 0, 0, 0,42.42]]
                        ],
                        [
                            "eid81",
                            "location",
                            2319,
                            75,
                            "linear",
                            "${arrow}",
                            [[384.1, 369.58, 0, 0, 0, 0,0],[355.49, 338.25, 0, 0, 0, 0,42.43]]
                        ],
                        [
                            "eid82",
                            "location",
                            2394,
                            69,
                            "linear",
                            "${arrow}",
                            [[355.49, 338.26, 0, 0, 0, 0,0],[383.03, 369.57, 0, 0, 0, 0,41.7]]
                        ],
                        [
                            "eid83",
                            "location",
                            2463,
                            73,
                            "linear",
                            "${arrow}",
                            [[383.04, 369.58, 0, 0, 0, 0,0],[355.49, 338.25, 0, 0, 0, 0,41.72]]
                        ],
                        [
                            "eid96",
                            "location",
                            2750,
                            66,
                            "linear",
                            "${arrow}",
                            [[361.98, 332.26, 0, 0, 0, 0,0],[392.49, 360.5, 0, 0, 0, 0,41.57]]
                        ],
                        [
                            "eid97",
                            "location",
                            2816,
                            65,
                            "linear",
                            "${arrow}",
                            [[392.49, 360.52, 0, 0, 0, 0,0],[361.3, 332.23, 0, 0, 0, 0,42.11]]
                        ],
                        [
                            "eid98",
                            "location",
                            2881,
                            71,
                            "linear",
                            "${arrow}",
                            [[361.3, 332.25, 0, 0, 0, 0,0],[392.49, 360.5, 0, 0, 0, 0,42.08]]
                        ],
                        [
                            "eid99",
                            "location",
                            2952,
                            65,
                            "linear",
                            "${arrow}",
                            [[392.49, 360.52, 0, 0, 0, 0,0],[361.3, 332.26, 0, 0, 0, 0,42.09]]
                        ],
                        [
                            "eid102",
                            "location",
                            3250,
                            89,
                            "linear",
                            "${arrow}",
                            [[376.5, 322.25, 0, 0, 0, 0,0],[405.63, 350.5, 0, 0, 0, 0,40.58]]
                        ],
                        [
                            "eid103",
                            "location",
                            3339,
                            81,
                            "linear",
                            "${arrow}",
                            [[405.64, 350.52, 0, 0, 0, 0,0],[380.3, 320.23, 0, 0, 0, 0,39.49]]
                        ],
                        [
                            "eid104",
                            "location",
                            3420,
                            80,
                            "linear",
                            "${arrow}",
                            [[380.3, 320.25, 0, 0, 0, 0,0],[428.5, 383.23, 0, 0, 0, 0,79.31]]
                        ],
                        [
                            "eid105",
                            "location",
                            3500,
                            81,
                            "linear",
                            "${arrow}",
                            [[428.5, 383.25, 0, 0, 0, 0,0],[382.3, 325.48, 0, 0, 0, 0,73.97]]
                        ],
                        [
                            "eid106",
                            "location",
                            3581,
                            55,
                            "linear",
                            "${arrow}",
                            [[382.3, 325.5, 0, 0, 0, 0,0],[419.3, 371.17, 0, 0, 0, 0,58.78]]
                        ],
                        [
                            "eid107",
                            "location",
                            3636,
                            55,
                            "linear",
                            "${arrow}",
                            [[419.3, 371.18, 0, 0, 0, 0,0],[392.49, 336.23, 0, 0, 0, 0,44.05]]
                        ],
                        [
                            "eid108",
                            "location",
                            3691,
                            59,
                            "linear",
                            "${arrow}",
                            [[392.5, 336.25, 0, 0, 0, 0,0],[418.97, 383.23, 0, 0, 0, 0,53.92]]
                        ],
                        [
                            "eid109",
                            "location",
                            3750,
                            66,
                            "linear",
                            "${arrow}",
                            [[418.98, 383.25, 0, 0, 0, 0,0],[380.3, 318.25, 0, 0, 0, 0,75.64]]
                        ],
                        [
                            "eid331",
                            "scaleX",
                            8600,
                            220,
                            "linear",
                            "${computer}",
                            '1.86957',
                            '1.01242'
                        ],
                        [
                            "eid498",
                            "display",
                            14200,
                            0,
                            "linear",
                            "${_4-f_04}",
                            'none',
                            'block'
                        ],
                        [
                            "eid590",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_04}",
                            'block',
                            'none'
                        ],
                        [
                            "eid736",
                            "opacity",
                            29598,
                            200,
                            "linear",
                            "${_7_11}",
                            '0',
                            '1'
                        ],
                        [
                            "eid506",
                            "display",
                            15000,
                            0,
                            "linear",
                            "${_4-f_32}",
                            'none',
                            'block'
                        ],
                        [
                            "eid582",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_32}",
                            'block',
                            'none'
                        ],
                        [
                            "eid686",
                            "rotateZ",
                            21196,
                            3597,
                            "linear",
                            "${Rectangle6}",
                            '0deg',
                            '1440deg'
                        ],
                        [
                            "eid820",
                            "rotateZ",
                            24793,
                            8,
                            "linear",
                            "${Rectangle6}",
                            '1440deg',
                            '2880deg'
                        ],
                        [
                            "eid233",
                            "display",
                            6200,
                            0,
                            "linear",
                            "${_2-2_072}",
                            'none',
                            'block'
                        ],
                        [
                            "eid250",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_072}",
                            'block',
                            'none'
                        ],
                        [
                            "eid627",
                            "display",
                            20700,
                            0,
                            "linear",
                            "${_5_06}",
                            'none',
                            'block'
                        ],
                        [
                            "eid650",
                            "display",
                            24000,
                            0,
                            "linear",
                            "${_5_06}",
                            'block',
                            'none'
                        ],
                        [
                            "eid760",
                            "scaleX",
                            31100,
                            601,
                            "linear",
                            "${_91_06}",
                            '2',
                            '1'
                        ],
                        [
                            "eid352",
                            "display",
                            9200,
                            0,
                            "linear",
                            "${_2_44}",
                            'none',
                            'block'
                        ],
                        [
                            "eid407",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_44}",
                            'block',
                            'none'
                        ],
                        [
                            "eid237",
                            "display",
                            6600,
                            0,
                            "linear",
                            "${_2-2_15}",
                            'none',
                            'block'
                        ],
                        [
                            "eid246",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_15}",
                            'block',
                            'none'
                        ],
                        [
                            "eid749",
                            "scaleY",
                            30100,
                            200,
                            "linear",
                            "${_7_19}",
                            '3.5',
                            '1'
                        ],
                        [
                            "eid502",
                            "display",
                            14600,
                            0,
                            "linear",
                            "${_4-f_10}",
                            'none',
                            'block'
                        ],
                        [
                            "eid586",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_10}",
                            'block',
                            'none'
                        ],
                        [
                            "eid460",
                            "display",
                            0,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'none'
                        ],
                        [
                            "eid457",
                            "display",
                            10633,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'none'
                        ],
                        [
                            "eid444",
                            "display",
                            11000,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'none'
                        ],
                        [
                            "eid445",
                            "display",
                            11100,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid447",
                            "display",
                            11200,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid449",
                            "display",
                            11300,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid451",
                            "display",
                            11400,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid453",
                            "display",
                            11500,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid461",
                            "display",
                            11600,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid463",
                            "display",
                            11700,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid465",
                            "display",
                            11800,
                            0,
                            "easeInElastic",
                            "${p3-ren2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid91",
                            "location",
                            2536,
                            214,
                            "linear",
                            "${car}",
                            [[-58.5, 319.5, 0, 0, 0, 0,0],[165.5, 496.5, 0, 0, 0, 0,285.49]]
                        ],
                        [
                            "eid273",
                            "location",
                            7700,
                            200,
                            "linear",
                            "${cart2}",
                            [[-115.5, 284, 0, 0, 0, 0,0],[180.5, 284, 0, 0, 0, 0,296]]
                        ],
                        [
                            "eid556",
                            "display",
                            16700,
                            0,
                            "linear",
                            "${_4_122222227}",
                            'none',
                            'block'
                        ],
                        [
                            "eid566",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4_122222227}",
                            'block',
                            'none'
                        ],
                        [
                            "eid593",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${Rectangle}",
                            'none',
                            'block'
                        ],
                        [
                            "eid696",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${Rectangle}",
                            'block',
                            'none'
                        ],
                        [
                            "eid761",
                            "scaleY",
                            31100,
                            601,
                            "linear",
                            "${_91_06}",
                            '2',
                            '1'
                        ],
                        [
                            "eid720",
                            "display",
                            28000,
                            0,
                            "linear",
                            "${_6-1_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid723",
                            "display",
                            29000,
                            0,
                            "linear",
                            "${_6-1_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid281",
                            "display",
                            7567,
                            0,
                            "linear",
                            "${p2-bg4}",
                            'none',
                            'block'
                        ],
                        [
                            "eid230",
                            "display",
                            8767,
                            0,
                            "linear",
                            "${p2-bg4}",
                            'block',
                            'block'
                        ],
                        [
                            "eid425",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${p2-bg4}",
                            'block',
                            'none'
                        ],
                        [
                            "eid330",
                            "opacity",
                            8600,
                            220,
                            "linear",
                            "${computer}",
                            '0',
                            '1'
                        ],
                        [
                            "eid148",
                            "display",
                            0,
                            0,
                            "linear",
                            "${running1}",
                            'none',
                            'none'
                        ],
                        [
                            "eid137",
                            "display",
                            4000,
                            0,
                            "linear",
                            "${running1}",
                            'none',
                            'none'
                        ],
                        [
                            "eid138",
                            "display",
                            4200,
                            0,
                            "linear",
                            "${running1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid161",
                            "display",
                            4400,
                            0,
                            "linear",
                            "${running1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid163",
                            "display",
                            4600,
                            0,
                            "linear",
                            "${running1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid165",
                            "display",
                            4800,
                            0,
                            "linear",
                            "${running1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid167",
                            "display",
                            5000,
                            0,
                            "linear",
                            "${running1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid169",
                            "display",
                            5200,
                            0,
                            "linear",
                            "${running1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid186",
                            "display",
                            5400,
                            0,
                            "linear",
                            "${running1}",
                            'none',
                            'none'
                        ],
                        [
                            "eid353",
                            "display",
                            9300,
                            0,
                            "linear",
                            "${_2_45}",
                            'none',
                            'block'
                        ],
                        [
                            "eid406",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_45}",
                            'block',
                            'none'
                        ],
                        [
                            "eid622",
                            "display",
                            20700,
                            0,
                            "linear",
                            "${_5_032}",
                            'none',
                            'block'
                        ],
                        [
                            "eid652",
                            "display",
                            24181,
                            0,
                            "linear",
                            "${_5_032}",
                            'block',
                            'none'
                        ],
                        [
                            "eid497",
                            "display",
                            14100,
                            0,
                            "linear",
                            "${_4-f_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid591",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid486",
                            "location",
                            11800,
                            200,
                            "linear",
                            "${_4_03}",
                            [[520.5, 721.5, 0, 0, 0, 0,0],[510.83, 1026.83, 0, 0, 0, 0,305.48]]
                        ],
                        [
                            "eid489",
                            "location",
                            12000,
                            1186,
                            "linear",
                            "${_4_03}",
                            [[510.83, 1026.83, 0, 0, 0, 0,0],[445.05, 932.61, -55.14, -0.98, -122.73, -2.18,120.61],[401.15, 1037.11, -50.35, 8.38, -67.29, 11.2,237.7],[353.1, 967.24, -47.32, 1.85, -55.08, 2.16,325.54],[318.93, 1035.64, -74.54, 10.22, -48.54, 6.66,404.58],[255.89, 1013.48, -42.56, -1.89, -73.36, -3.26,472.44],[210.73, 1042, -157.19, 2.8, -41.11, 0.73,526.83],[195.17, 1042.83, 0, 0, 0, 0,567.18]]
                        ],
                        [
                            "eid594",
                            "display",
                            19250,
                            0,
                            "linear",
                            "${_5_28}",
                            'none',
                            'block'
                        ],
                        [
                            "eid689",
                            "display",
                            26000,
                            0,
                            "linear",
                            "${_5_28}",
                            'block',
                            'none'
                        ],
                        [
                            "eid500",
                            "display",
                            14400,
                            0,
                            "linear",
                            "${_4-f_08}",
                            'none',
                            'block'
                        ],
                        [
                            "eid588",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_08}",
                            'block',
                            'none'
                        ],
                        [
                            "eid19",
                            "display",
                            2000,
                            0,
                            "easeInElastic",
                            "${arrow}",
                            'none',
                            'block'
                        ],
                        [
                            "eid110",
                            "display",
                            3816,
                            0,
                            "linear",
                            "${arrow}",
                            'block',
                            'block'
                        ],
                        [
                            "eid200",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${arrow}",
                            'block',
                            'none'
                        ],
                        [
                            "eid630",
                            "display",
                            21037,
                            0,
                            "linear",
                            "${_5_10}",
                            'none',
                            'block'
                        ],
                        [
                            "eid695",
                            "display",
                            24853,
                            0,
                            "linear",
                            "${_5_10}",
                            'block',
                            'none'
                        ],
                        [
                            "eid310",
                            "display",
                            8500,
                            0,
                            "linear",
                            "${_2_10}",
                            'none',
                            'block'
                        ],
                        [
                            "eid415",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_10}",
                            'block',
                            'none'
                        ],
                        [
                            "eid278",
                            "display",
                            7900,
                            0,
                            "linear",
                            "${_2_03_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid418",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_03_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid769",
                            "top",
                            31701,
                            1000,
                            "linear",
                            "${_911_022}",
                            '953px',
                            '357px'
                        ],
                        [
                            "eid561",
                            "opacity",
                            17429,
                            821,
                            "linear",
                            "${_4_132}",
                            '0',
                            '1'
                        ],
                        [
                            "eid731",
                            "opacity",
                            29100,
                            200,
                            "linear",
                            "${_7_03}",
                            '0',
                            '1'
                        ],
                        [
                            "eid349",
                            "display",
                            9020,
                            0,
                            "linear",
                            "${_2_30}",
                            'none',
                            'block'
                        ],
                        [
                            "eid408",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_30}",
                            'block',
                            'none'
                        ],
                        [
                            "eid517",
                            "display",
                            16000,
                            0,
                            "linear",
                            "${_4-f_26}",
                            'none',
                            'block'
                        ],
                        [
                            "eid572",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_26}",
                            'block',
                            'none'
                        ],
                        [
                            "eid16",
                            "opacity",
                            1000,
                            1000,
                            "linear",
                            "${title}",
                            '0',
                            '1'
                        ],
                        [
                            "eid119",
                            "display",
                            4000,
                            0,
                            "linear",
                            "${running2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid160",
                            "display",
                            4200,
                            0,
                            "linear",
                            "${running2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid162",
                            "display",
                            4400,
                            0,
                            "linear",
                            "${running2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid164",
                            "display",
                            4600,
                            0,
                            "linear",
                            "${running2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid166",
                            "display",
                            4800,
                            0,
                            "linear",
                            "${running2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid168",
                            "display",
                            5000,
                            0,
                            "linear",
                            "${running2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid170",
                            "display",
                            5200,
                            0,
                            "linear",
                            "${running2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid185",
                            "display",
                            5400,
                            0,
                            "linear",
                            "${running2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid277",
                            "display",
                            7900,
                            0,
                            "linear",
                            "${_2_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid419",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_07}",
                            'block',
                            'none'
                        ],
                        [
                            "eid532",
                            "location",
                            16322,
                            378,
                            "linear",
                            "${_4_07}",
                            [[375, 495, 0, 0, 0, 0,0],[382, 833, 0, 0, 0, 0,338.07]]
                        ],
                        [
                            "eid311",
                            "display",
                            8600,
                            0,
                            "linear",
                            "${_2_12}",
                            'none',
                            'block'
                        ],
                        [
                            "eid414",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_12}",
                            'block',
                            'none'
                        ],
                        [
                            "eid197",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${house}",
                            'block',
                            'none'
                        ],
                        [
                            "eid770",
                            "display",
                            31701,
                            0,
                            "linear",
                            "${_911_022}",
                            'none',
                            'block'
                        ],
                        [
                            "eid781",
                            "display",
                            33400,
                            0,
                            "linear",
                            "${_911_022}",
                            'block',
                            'none'
                        ],
                        [
                            "eid458",
                            "display",
                            10633,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid443",
                            "display",
                            11000,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'block',
                            'block'
                        ],
                        [
                            "eid446",
                            "display",
                            11100,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid448",
                            "display",
                            11200,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid450",
                            "display",
                            11300,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid452",
                            "display",
                            11400,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid454",
                            "display",
                            11500,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid462",
                            "display",
                            11600,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid464",
                            "display",
                            11700,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid466",
                            "display",
                            11800,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'none',
                            'block'
                        ],
                        [
                            "eid494",
                            "display",
                            13900,
                            0,
                            "easeInElastic",
                            "${p3-ren1}",
                            'block',
                            'none'
                        ],
                        [
                            "eid467",
                            "display",
                            11600,
                            0,
                            "easeInElastic",
                            "${_3_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid493",
                            "display",
                            13900,
                            0,
                            "easeInElastic",
                            "${_3_03}",
                            'block',
                            'none'
                        ],
                        [
                            "eid708",
                            "location",
                            26500,
                            1200,
                            "linear",
                            "${_6_06}",
                            [[489.92, 1016.06, 0, 0, 0, 0,0],[455.48, 1021.91, -26.75, 21.81, -64.01, 52.18,37.53],[219.93, 977.76, -730.54, -354.43, -227.93, -110.58,279.27],[157.86, 499.51, 221.91, -181.82, 200.46, -164.24,837.26],[374.25, 401.26, 337.26, -7.03, 212.51, -4.43,1079.25],[625.09, 522.75, 277.49, 427.74, 110.99, 171.09,1368.25],[639.26, 887.06, -156.19, 246.5, -164.21, 259.15,1754.02],[506, 1005.95, 0, 0, 0, 0,1934.68]]
                        ],
                        [
                            "eid260",
                            "display",
                            7500,
                            0,
                            "linear",
                            "${_2_03_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid423",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${_2_03_07}",
                            'block',
                            'none'
                        ],
                        [
                            "eid727",
                            "display",
                            29000,
                            0,
                            "linear",
                            "${Rectangle2}",
                            'none',
                            'block'
                        ],
                        [
                            "eid759",
                            "display",
                            31100,
                            0,
                            "linear",
                            "${Rectangle2}",
                            'block',
                            'none'
                        ],
                        [
                            "eid557",
                            "height",
                            16700,
                            513,
                            "linear",
                            "${_4_122222227}",
                            '18px',
                            '41px'
                        ],
                        [
                            "eid728",
                            "scaleX",
                            29100,
                            200,
                            "linear",
                            "${_7_03}",
                            '3.5',
                            '1'
                        ],
                        [
                            "eid766",
                            "display",
                            31400,
                            0,
                            "linear",
                            "${_91_07}",
                            'none',
                            'block'
                        ],
                        [
                            "eid779",
                            "display",
                            33400,
                            0,
                            "linear",
                            "${_91_07}",
                            'block',
                            'none'
                        ],
                        [
                            "eid235",
                            "display",
                            6400,
                            0,
                            "linear",
                            "${_2-2_11}",
                            'none',
                            'block'
                        ],
                        [
                            "eid248",
                            "display",
                            7300,
                            0,
                            "linear",
                            "${_2-2_11}",
                            'block',
                            'none'
                        ],
                        [
                            "eid512",
                            "display",
                            15600,
                            0,
                            "linear",
                            "${_4-f_22}",
                            'none',
                            'block'
                        ],
                        [
                            "eid576",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_22}",
                            'block',
                            'none'
                        ],
                        [
                            "eid429",
                            "display",
                            10633,
                            0,
                            "linear",
                            "${running4}",
                            'block',
                            'none'
                        ],
                        [
                            "eid510",
                            "display",
                            15400,
                            0,
                            "linear",
                            "${_4-f_20}",
                            'none',
                            'block'
                        ],
                        [
                            "eid578",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_20}",
                            'block',
                            'none'
                        ],
                        [
                            "eid516",
                            "display",
                            15900,
                            0,
                            "linear",
                            "${_4-f_25}",
                            'none',
                            'block'
                        ],
                        [
                            "eid573",
                            "display",
                            19000,
                            0,
                            "linear",
                            "${_4-f_25}",
                            'block',
                            'none'
                        ],
                        [
                            "eid306",
                            "height",
                            8068,
                            0,
                            "linear",
                            "${_2_23}",
                            '163px',
                            '163px'
                        ],
                        [
                            "eid307",
                            "height",
                            8250,
                            99,
                            "linear",
                            "${_2_23}",
                            '163px',
                            '60px'
                        ],
                        [
                            "eid304",
                            "height",
                            8349,
                            98,
                            "linear",
                            "${_2_23}",
                            '60px',
                            '163px'
                        ],
                        [
                            "eid611",
                            "opacity",
                            21911,
                            839,
                            "linear",
                            "${_5f_062}",
                            '1',
                            '0.21138211382114'
                        ],
                        [
                            "eid763",
                            "opacity",
                            31100,
                            601,
                            "linear",
                            "${_91_06}",
                            '0',
                            '1'
                        ],
                        [
                            "eid258",
                            "opacity",
                            7300,
                            200,
                            "linear",
                            "${_2_27_03_03}",
                            '0',
                            '1'
                        ],
                        [
                            "eid206",
                            "display",
                            4000,
                            0,
                            "linear",
                            "${shadow}",
                            'none',
                            'block'
                        ],
                        [
                            "eid209",
                            "display",
                            5400,
                            0,
                            "linear",
                            "${shadow}",
                            'block',
                            'none'
                        ],
                        [
                            "eid688",
                            "originY",
                            24801,
                            0,
                            "linear",
                            "${Rectangle6}",
                            '100%',
                            '100%'
                        ],
                        [
                            "eid718",
                            "scaleY",
                            28000,
                            302,
                            "linear",
                            "${_6-1_03}",
                            '3',
                            '1'
                        ],
                        [
                            "eid784",
                            "display",
                            33400,
                            0,
                            "linear",
                            "${_10_03}",
                            'none',
                            'block'
                        ]
                    ]
                }
            },
            "running2": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            type: 'image',
                            id: 'psd-1_12_032',
                            rect: ['0px', '0px', '313px', '504px', 'auto', 'auto'],
                            fill: ['rgba(0,0,0,0)', 'images/psd-1_12_03.png', '0px', '0px']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '313px', '504px']
                        }
                    }
                },
                timeline: {
                    duration: 3842,
                    autoPlay: true,
                    data: [
                        [
                            "eid114",
                            "left",
                            3842,
                            0,
                            "linear",
                            "${psd-1_12_032}",
                            '0px',
                            '0px'
                        ],
                        [
                            "eid113",
                            "top",
                            3842,
                            0,
                            "linear",
                            "${psd-1_12_032}",
                            '0px',
                            '0px'
                        ]
                    ]
                }
            },
            "running3": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            id: 'psd-1_12_03_03',
                            type: 'image',
                            rect: ['0px', '0px', '429px', '479px', 'auto', 'auto'],
                            fill: ['rgba(0,0,0,0)', 'images/psd-1_12_03_03.png', '0px', '0px']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '429px', '479px']
                        }
                    }
                },
                timeline: {
                    duration: 0,
                    autoPlay: true,
                    data: [

                    ]
                }
            },
            "running1": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            type: 'image',
                            id: 'psd-1_112',
                            rect: ['0px', '0px', '430px', '475px', 'auto', 'auto'],
                            fill: ['rgba(0,0,0,0)', 'images/psd-1_112.png', '0px', '0px']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '430px', '475px']
                        }
                    }
                },
                timeline: {
                    duration: 0,
                    autoPlay: true,
                    data: [

                    ]
                }
            },
            "running4": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            type: 'image',
                            display: 'none',
                            rect: ['0px', '0px', '637px', '280px', 'auto', 'auto'],
                            id: 'psd-1_12_03_03_03',
                            fill: ['rgba(0,0,0,0)', 'images/psd-1_12_03_03_03.png', '0px', '0px']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '637px', '280px']
                        }
                    }
                },
                timeline: {
                    duration: 6000,
                    autoPlay: true,
                    data: [
                        [
                            "eid187",
                            "display",
                            5400,
                            0,
                            "linear",
                            "${psd-1_12_03_03_03}",
                            'none',
                            'block'
                        ],
                        [
                            "eid188",
                            "display",
                            6000,
                            0,
                            "linear",
                            "${psd-1_12_03_03_03}",
                            'block',
                            'none'
                        ]
                    ]
                }
            },
            "shadow": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            rect: ['0px', '0px', '172px', '15px', 'auto', 'auto'],
                            id: '_1-22_03',
                            type: 'image',
                            fill: ['rgba(0,0,0,0)', 'images/1-22_03.png', '0px', '0px']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '172px', '15px']
                        }
                    }
                },
                timeline: {
                    duration: 0,
                    autoPlay: true,
                    data: [

                    ]
                }
            },
            "cart": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            rect: ['0px', '0px', '197px', '256px', 'auto', 'auto'],
                            overflow: 'hidden',
                            id: '_2_202',
                            type: 'image',
                            fill: ['rgba(0,0,0,0)', 'images/2_20.png', '0px', '0px']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '197px', '256px']
                        }
                    }
                },
                timeline: {
                    duration: 0,
                    autoPlay: true,
                    data: [

                    ]
                }
            },
            "computer": {
                version: "5.0.1",
                minimumCompatibleVersion: "5.0.0",
                build: "5.0.1.386",
                scaleToFit: "none",
                centerStage: "none",
                resizeInstances: false,
                content: {
                    dom: [
                        {
                            rect: ['0px', '0px', '196px', '161px', 'auto', 'auto'],
                            id: '_2_33',
                            type: 'image',
                            display: 'none',
                            fill: ['rgba(0,0,0,0)', 'images/2_33.png', '0px', '0px', '100%', '100%', 'no-repeat']
                        }
                    ],
                    style: {
                        '${symbolSelector}': {
                            rect: [null, null, '196px', '161px']
                        }
                    }
                },
                timeline: {
                    duration: 8600,
                    autoPlay: true,
                    data: [
                        [
                            "eid316",
                            "display",
                            8600,
                            0,
                            "linear",
                            "${_2_33}",
                            'none',
                            'block'
                        ]
                    ]
                }
            }
        };

    AdobeEdge.registerCompositionDefn(compId, symbols, fonts, scripts, resources, opts);

    if (!window.edge_authoring_mode) AdobeEdge.getComposition(compId).load("xxd-canvas_edgeActions.js");
})("EDGE-6264092");
