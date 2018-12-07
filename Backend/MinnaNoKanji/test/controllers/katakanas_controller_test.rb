require 'test_helper'

class KatakanasControllerTest < ActionDispatch::IntegrationTest
  setup do
    @katakana = katakanas(:one)
  end

  test "should get index" do
    get katakanas_url
    assert_response :success
  end

  test "should get new" do
    get new_katakana_url
    assert_response :success
  end

  test "should create katakana" do
    assert_difference('Katakana.count') do
      post katakanas_url, params: { katakana: { explicacion: @katakana.explicacion, leccion: @katakana.leccion } }
    end

    assert_redirected_to katakana_url(Katakana.last)
  end

  test "should show katakana" do
    get katakana_url(@katakana)
    assert_response :success
  end

  test "should get edit" do
    get edit_katakana_url(@katakana)
    assert_response :success
  end

  test "should update katakana" do
    patch katakana_url(@katakana), params: { katakana: { explicacion: @katakana.explicacion, leccion: @katakana.leccion } }
    assert_redirected_to katakana_url(@katakana)
  end

  test "should destroy katakana" do
    assert_difference('Katakana.count', -1) do
      delete katakana_url(@katakana)
    end

    assert_redirected_to katakanas_url
  end
end
