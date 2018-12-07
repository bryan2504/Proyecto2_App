class KatakanasController < ApplicationController
  before_action :set_katakana, only: [:show, :edit, :update, :destroy]

  # GET /katakanas
  # GET /katakanas.json
  def index
    @katakanas = Katakana.all
  end

  # GET /katakanas/1
  # GET /katakanas/1.json
  def show
  end

  # GET /katakanas/new
  def new
    @katakana = Katakana.new
  end

  # GET /katakanas/1/edit
  def edit
  end

  # POST /katakanas
  # POST /katakanas.json
  def create
    @katakana = Katakana.new(katakana_params)

    respond_to do |format|
      if @katakana.save
        format.html { redirect_to @katakana, notice: 'Katakana was successfully created.' }
        format.json { render :show, status: :created, location: @katakana }
      else
        format.html { render :new }
        format.json { render json: @katakana.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /katakanas/1
  # PATCH/PUT /katakanas/1.json
  def update
    respond_to do |format|
      if @katakana.update(katakana_params)
        format.html { redirect_to @katakana, notice: 'Katakana was successfully updated.' }
        format.json { render :show, status: :ok, location: @katakana }
      else
        format.html { render :edit }
        format.json { render json: @katakana.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /katakanas/1
  # DELETE /katakanas/1.json
  def destroy
    @katakana.destroy
    respond_to do |format|
      format.html { redirect_to katakanas_url, notice: 'Katakana was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_katakana
      @katakana = Katakana.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def katakana_params
      params.require(:katakana).permit(:leccion, :explicacion)
    end
end
