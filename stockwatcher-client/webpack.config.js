var path = require('path');
//process.noDeprecation = true

module.exports = {
    entry: [
            './src/app.jsx',
            path.resolve(__dirname, 'style', 'bootstrap.scss'),
    ],
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname,
        filename: 'bundle.js',
        publicPath: './'
    },
	watch: true,
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                loader: "babel-loader",
            
                query: {
                    cacheDirectory: true,
                    presets: ['es2015', 'react']
                }
            },
    		{
    			test: /\.woff(2)?$|\.ttf$|\.eot$|\.svg$/,
    			
    				     
    				    	  loader: "file-loader",
    				     
    				     
            
    		},
    		{
    			test: /\.scss$/,
    			use: [
    			      {
    			    	  loader: "style-loader"
    			      },
    			      {
    			    	  loader: "css-loader"
	    		      },
	    		      {
	    		    	  loader: "sass-loader"
	    		      }
    		     ]
    		},
    		{
    			test: /bootstrap\/js\//, 
    			loader: 'imports?jQuery=jquery'
    		},
    		{
    			test: /\.less$/, 
    				use: [
    				      "style-loader",
    				      "css-loader",
    				      "less-loader"
    				      ]
    		}
        ]
    }
};